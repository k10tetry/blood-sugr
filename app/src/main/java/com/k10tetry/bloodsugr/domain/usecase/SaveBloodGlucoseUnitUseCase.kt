package com.k10tetry.bloodsugr.domain.usecase

import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import com.k10tetry.bloodsugr.domain.repository.LocalRepository
import javax.inject.Inject

class SaveBloodGlucoseUnitUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(units: BloodGlucoseUnits) =
        localRepository.saveBloodGlucoseUnit(units)

}