package com.k10tetry.bloodsugr.domain.usecase

import com.k10tetry.bloodsugr.domain.repository.LocalRepository
import javax.inject.Inject

class GetBloodGlucoseListUseCase @Inject constructor(private val localRepository: LocalRepository) {

    operator fun invoke() = localRepository.getBloodGlucoseList()

}