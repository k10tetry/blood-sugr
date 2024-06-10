package com.k10tetry.bloodsugr.domain.usecase

import com.k10tetry.bloodsugr.domain.model.BloodGlucoseModel
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import com.k10tetry.bloodsugr.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveAndGetAverageUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val convertBloodGlucoseUseCase: ConvertBloodGlucoseUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(model: BloodGlucoseModel, to: BloodGlucoseUnits) =
        withContext(dispatcher) {

            localRepository.saveBloodGlucose(model)

            val list = localRepository.getBloodGlucoseList().first()
            var total = 0.0

            list.forEach {
                total += convertBloodGlucoseUseCase.invoke(Pair(it.value, it.units), to).first
            }

            return@withContext total / list.count()
        }

}