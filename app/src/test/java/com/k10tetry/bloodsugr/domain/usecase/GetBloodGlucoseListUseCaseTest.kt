package com.k10tetry.bloodsugr.domain.usecase

import com.google.common.truth.Truth
import com.k10tetry.bloodsugr.common.round
import com.k10tetry.bloodsugr.data.local.repository.FakeLocalRepositoryImpl
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseModel
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class BloodGlucoseUseCaseTest {

    private val localRepository = FakeLocalRepositoryImpl()
    private val getBloodGlucoseListUseCase = GetBloodGlucoseListUseCase(localRepository)
    private val convertBloodGlucoseUseCase = ConvertBloodGlucoseUseCase()
    private val getAverageBloodGlucoseUseCase =
        GetAverageBloodGlucoseUseCase(getBloodGlucoseListUseCase, convertBloodGlucoseUseCase)
    private val getBloodGlucoseUnitUseCase = GetBloodGlucoseUnitUseCase(localRepository)
    private val saveBloodGlucoseUseCase = SaveBloodGlucoseUseCase(localRepository)
    private val saveBloodGlucoseUnitUseCase = SaveBloodGlucoseUnitUseCase(localRepository)

    @Test
    fun `Save blood glucose unit`() = runTest(UnconfinedTestDispatcher()) {
        saveBloodGlucoseUnitUseCase(BloodGlucoseUnits.MILLI_MOLES_LTR)
        val unit = getBloodGlucoseUnitUseCase().first()
        Truth.assertThat(unit).isEqualTo(BloodGlucoseUnits.MILLI_MOLES_LTR)
    }

    @Test
    fun `Save blood glucose`() = runTest(UnconfinedTestDispatcher()) {
        val model = BloodGlucoseModel(10.0, BloodGlucoseUnits.MILLI_GRAM_DL, 100, 1)
        val model2 = BloodGlucoseModel(20.0, BloodGlucoseUnits.MILLI_GRAM_DL, 200, 2)
        saveBloodGlucoseUseCase(model)
        saveBloodGlucoseUseCase(model2)
        val dataList = getBloodGlucoseListUseCase().first()

        Truth.assertThat(dataList.size).isEqualTo(2)
        Truth.assertThat(dataList.last()).isEqualTo(model2)
    }

    @Test
    fun `Get average blood glucose as Milli Moles`() = runTest(UnconfinedTestDispatcher()) {

        repeat(5) {
            val unit =
                if (it % 2 == 0) BloodGlucoseUnits.MILLI_GRAM_DL else BloodGlucoseUnits.MILLI_MOLES_LTR
            saveBloodGlucoseUseCase(BloodGlucoseModel(it + 20.0, unit, 100, it + 1L))
        }

        val result = getAverageBloodGlucoseUseCase(BloodGlucoseUnits.MILLI_MOLES_LTR)

        Truth.assertThat(result.round()).isEqualTo(9.53)
    }

}