package com.k10tetry.bloodsugr.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k10tetry.bloodsugr.common.ErrorType
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseModel
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import com.k10tetry.bloodsugr.domain.usecase.ConvertBloodGlucoseUseCase
import com.k10tetry.bloodsugr.domain.usecase.GetAverageBloodGlucoseUseCase
import com.k10tetry.bloodsugr.domain.usecase.GetBloodGlucoseListUseCase
import com.k10tetry.bloodsugr.domain.usecase.GetBloodGlucoseUnitUseCase
import com.k10tetry.bloodsugr.domain.usecase.SaveBloodGlucoseUnitUseCase
import com.k10tetry.bloodsugr.domain.usecase.SaveBloodGlucoseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveBloodGlucoseUnitUseCase: SaveBloodGlucoseUnitUseCase,
    private val getBloodGlucoseUnitUseCase: GetBloodGlucoseUnitUseCase,
    private val getAverageBloodGlucoseUseCase: GetAverageBloodGlucoseUseCase,
    private val convertBloodGlucoseUseCase: ConvertBloodGlucoseUseCase,
    private val saveBloodGlucoseUseCase: SaveBloodGlucoseUseCase,
    private val getBloodGlucoseListUseCase: GetBloodGlucoseListUseCase
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

    init {
        viewModelScope.launch {

            launch {
                getBloodGlucoseUnitUseCase().catch {
                    _toastState.emit(getErrorType(it))
                }.collect {
                    _unitState.value = it
                    _averageState.value = getAverageBloodGlucoseUseCase(it)
                }
            }

            launch {
                getBloodGlucoseListUseCase().collect {
                    _measurementListState.value = it
                }
            }
        }
    }

    private fun getErrorType(error: Throwable) = when (error) {
        is IOException -> ErrorType.NETWORK_ERROR
        is JSONException -> ErrorType.PARSING_ERROR
        else -> ErrorType.OTHER
    }

    fun saveUnits(units: BloodGlucoseUnits) {
        viewModelScope.launch {
            saveBloodGlucoseUnitUseCase(units)
        }
    }

    fun convertInputMeasurement(text: String?, unit: BloodGlucoseUnits) {
        viewModelScope.launch {

            val from = when (unit) {
                BloodGlucoseUnits.MILLI_MOLES_LTR -> BloodGlucoseUnits.MILLI_GRAM_DL
                BloodGlucoseUnits.MILLI_GRAM_DL -> BloodGlucoseUnits.MILLI_MOLES_LTR
            }

            val result = convertBloodGlucoseUseCase(Pair(text?.toDoubleOrNull() ?: 0.0, from), unit)
            _inputState.value = result.first
        }
    }

    fun saveMeasurement(model: BloodGlucoseModel) {
        viewModelScope.launch {
            saveBloodGlucoseUseCase(model)
            _averageState.value = getAverageBloodGlucoseUseCase(model.units)
        }
    }


}