package com.k10tetry.bloodsugr.domain.usecase

import com.k10tetry.bloodsugr.domain.repository.LocalRepository
import javax.inject.Inject

class GetBloodGlucoseUnitUseCase @Inject constructor(private val localRepository: LocalRepository) {

    operator fun invoke() = localRepository.getBloodGlucoseUnit()

}