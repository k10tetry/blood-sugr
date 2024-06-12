package com.k10tetry.bloodsugr.domain.usecase

import com.k10tetry.bloodsugr.domain.model.BloodGlucoseModel
import com.k10tetry.bloodsugr.domain.repository.LocalRepository
import javax.inject.Inject

class SaveBloodGlucoseUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(model: BloodGlucoseModel) = localRepository.saveBloodGlucose(model)

}