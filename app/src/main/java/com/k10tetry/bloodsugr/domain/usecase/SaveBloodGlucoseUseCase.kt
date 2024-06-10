package com.k10tetry.bloodsugr.domain.usecase

import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import com.k10tetry.bloodsugr.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveBloodGlucoseUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(units: BloodGlucoseUnits) = withContext(dispatcher) {
        localRepository.saveBloodGlucoseUnit(units)
    }

}