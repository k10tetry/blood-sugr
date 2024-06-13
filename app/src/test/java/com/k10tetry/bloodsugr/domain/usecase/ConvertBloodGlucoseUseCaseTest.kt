package com.k10tetry.bloodsugr.domain.usecase

import com.google.common.truth.Truth
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import org.junit.Test

class ConvertBloodGlucoseUseCaseTest {

    private val convertBloodGlucoseUseCase = ConvertBloodGlucoseUseCase()

    @Test
    fun `Convert blood glucose from mmol to mg`() {
        val from = Pair(10.0, BloodGlucoseUnits.MILLI_MOLES_LTR)
        val result = Pair(10.0 * 18.0182, BloodGlucoseUnits.MILLI_GRAM_DL)
        Truth.assertThat(convertBloodGlucoseUseCase(from, result.second)).isEqualTo(result)
    }

    @Test
    fun `Convert blood glucose from mg to mmol`() {
        val from = Pair(10.0, BloodGlucoseUnits.MILLI_GRAM_DL)
        val result = Pair(10.0 * (1 / 18.0182), BloodGlucoseUnits.MILLI_MOLES_LTR)
        Truth.assertThat(convertBloodGlucoseUseCase(from, result.second)).isEqualTo(result)
    }

    @Test
    fun `Convert blood glucose from mmol to mmol`() {
        val from = Pair(10.0, BloodGlucoseUnits.MILLI_MOLES_LTR)
        Truth.assertThat(convertBloodGlucoseUseCase(from, from.second)).isEqualTo(from)
    }

}