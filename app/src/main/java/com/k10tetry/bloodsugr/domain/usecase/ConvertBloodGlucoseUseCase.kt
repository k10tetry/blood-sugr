package com.k10tetry.bloodsugr.domain.usecase

import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import javax.inject.Inject

class ConvertBloodGlucoseUseCase @Inject constructor() {

    operator fun invoke(
        from: Pair<Double, BloodGlucoseUnits>, to: BloodGlucoseUnits
    ): Pair<Double, BloodGlucoseUnits> {

        // If from and to BloodGlucoseUnits are same, return as it is
        if (from.second == to) {
            return from
        }

        return when (to) {
            BloodGlucoseUnits.MILLI_MOLES_LTR -> Pair(convertToMilliMoles(from.first), to)
            BloodGlucoseUnits.MILLI_GRAM_DL -> Pair(convertToMilliGram(from.first), to)
        }
    }

    // 1 mg/dL = 1/18.0182 mmol/L
    private fun convertToMilliMoles(milliGram: Double): Double = milliGram * (1 / CONVERSION_RATE)

    // 1 mmol/L = 18.0182 mg/dL
    private fun convertToMilliGram(milliMoles: Double): Double = milliMoles * CONVERSION_RATE

    companion object {
        private const val CONVERSION_RATE = 18.0182
    }

}