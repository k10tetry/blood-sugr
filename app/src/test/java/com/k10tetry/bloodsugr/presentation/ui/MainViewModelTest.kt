package com.k10tetry.bloodsugr.presentation.ui

import com.google.common.truth.Truth
import com.k10tetry.bloodsugr.MainDispatcherRule
import com.k10tetry.bloodsugr.common.round
import com.k10tetry.bloodsugr.data.local.repository.FakeLocalRepositoryImpl
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import com.k10tetry.bloodsugr.domain.usecase.ConvertBloodGlucoseUseCase
import com.k10tetry.bloodsugr.domain.usecase.GetAverageBloodGlucoseUseCase
import com.k10tetry.bloodsugr.domain.usecase.GetBloodGlucoseListUseCase
import com.k10tetry.bloodsugr.domain.usecase.GetBloodGlucoseUnitUseCase
import com.k10tetry.bloodsugr.domain.usecase.MainUseCase
import com.k10tetry.bloodsugr.domain.usecase.SaveBloodGlucoseUnitUseCase
import com.k10tetry.bloodsugr.domain.usecase.SaveBloodGlucoseUseCase
import com.k10tetry.bloodsugr.presentation.TestDispatcherImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    private val localRepository = FakeLocalRepositoryImpl()
    private val getBloodGlucoseListUseCase = GetBloodGlucoseListUseCase(localRepository)
    private val convertBloodGlucoseUseCase = ConvertBloodGlucoseUseCase()
    private val mainUseCase = MainUseCase(
        SaveBloodGlucoseUnitUseCase(localRepository),
        GetBloodGlucoseUnitUseCase(localRepository),
        GetAverageBloodGlucoseUseCase(getBloodGlucoseListUseCase, convertBloodGlucoseUseCase),
        convertBloodGlucoseUseCase,
        SaveBloodGlucoseUseCase(localRepository),
        getBloodGlucoseListUseCase
    )

    private val testDispatcherImpl = TestDispatcherImpl(mainDispatcherRule.testDispatcher)
    private val viewModel = MainViewModel(mainUseCase, testDispatcherImpl)

    @Test
    fun `Save blood glucose units`() = runTest {
        Truth.assertThat(viewModel.unitState.value).isEqualTo(BloodGlucoseUnits.MILLI_MOLES_LTR)

        viewModel.initialize()
        viewModel.saveUnits(BloodGlucoseUnits.MILLI_GRAM_DL)
        advanceUntilIdle()

        Truth.assertThat(viewModel.unitState.value).isEqualTo(BloodGlucoseUnits.MILLI_GRAM_DL)
    }

    @Test
    fun `Save measurement, and check average`() = runTest {
        viewModel.initialize()
        viewModel.saveMeasurement(10.0, BloodGlucoseUnits.MILLI_MOLES_LTR, 101)
        viewModel.saveMeasurement(20.0, BloodGlucoseUnits.MILLI_MOLES_LTR, 102)
        advanceUntilIdle()

        Truth.assertThat(viewModel.averageState.value).isEqualTo(15.0)
    }

    @Test
    fun `Convert input measurement, and check input`() = runTest {
        viewModel.convertInputMeasurement(10.0, BloodGlucoseUnits.MILLI_GRAM_DL)
        advanceUntilIdle()

        Truth.assertThat(viewModel.inputState.value.round()).isEqualTo(180.18)
    }

}