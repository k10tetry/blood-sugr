package com.k10tetry.bloodsugr.domain.usecase

import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAverageBloodGlucoseUseCase @Inject constructor(
    private val getBloodGlucoseListUseCase: GetBloodGlucoseListUseCase,
    private val convertBloodGlucoseUseCase: ConvertBloodGlucoseUseCase
) {

    suspend operator fun invoke(units: BloodGlucoseUnits): Double {
        val list = getBloodGlucoseListUseCase().first()

        if (list.isEmpty()) {
            return 0.0
        }

        var total = 0.0

        list.forEach {
            total += convertBloodGlucoseUseCase.invoke(Pair(it.value, it.units), units).first
        }

        return total / list.size
    }

}