package com.k10tetry.bloodsugr.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k10tetry.bloodsugr.common.ErrorType
import com.k10tetry.bloodsugr.common.SugrDispatcher
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseModel
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import com.k10tetry.bloodsugr.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase, private val dispatcher: SugrDispatcher
) : ViewModel() {

    private val _averageState = MutableStateFlow(0.0)
    val averageState = _averageState.asStateFlow()

    private val _unitState = MutableStateFlow(BloodGlucoseUnits.MILLI_GRAM_DL)
    val unitState = _unitState.asStateFlow()

    private val _inputState = MutableStateFlow(0.0)
    val inputState = _inputState.asStateFlow()

    private val _measurementListState = MutableStateFlow<List<BloodGlucoseModel>>(emptyList())
    val measurementListState = _measurementListState.asStateFlow()

    private val _toastState = MutableSharedFlow<ErrorType>()
    val toastState = _toastState.asSharedFlow()

    var previous = _unitState.value

    init {
        mainUseCase.getUnit().catch {
            _toastState.emit(getErrorType(it))
        }.onEach {
            previous = _unitState.value
            _unitState.value = it
            _averageState.value = mainUseCase.getAverage(it)
        }.flowOn(dispatcher.default).launchIn(viewModelScope)

        mainUseCase.getBloodGlucoseList().catch {
            _toastState.emit(getErrorType(it))
        }.onEach {
            _measurementListState.value = it
        }.flowOn(dispatcher.main).launchIn(viewModelScope)
    }

    private fun getErrorType(error: Throwable) = when (error) {
        is IOException -> ErrorType.NETWORK_ERROR
        is JSONException -> ErrorType.PARSING_ERROR
        else -> ErrorType.OTHER
    }

    fun saveUnits(units: BloodGlucoseUnits) {
        viewModelScope.launch {
            mainUseCase.saveUnit(units)
        }
    }

    fun convertInputMeasurement(inputMeasurement: Double, unit: BloodGlucoseUnits) {
        viewModelScope.launch(dispatcher.default) {
            val result = mainUseCase.convert(Pair(inputMeasurement, previous), unit)
            _inputState.value = result.first
        }
    }

    fun saveMeasurement(measurement: Double, unit: BloodGlucoseUnits, timeInMillis: Long) {
        viewModelScope.launch(dispatcher.default) {
            val model = BloodGlucoseModel(measurement, unit, timeInMillis)
            mainUseCase.saveBloodGlucose(model)
            _averageState.value = mainUseCase.getAverage(model.units)
        }
    }


}