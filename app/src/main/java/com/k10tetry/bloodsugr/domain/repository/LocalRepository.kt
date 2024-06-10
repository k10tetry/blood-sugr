package com.k10tetry.bloodsugr.domain.repository

import com.k10tetry.bloodsugr.domain.model.BloodGlucoseModel
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun saveBloodGlucose(model: BloodGlucoseModel)

    fun getBloodGlucoseList(): Flow<List<BloodGlucoseModel>>

    suspend fun saveBloodGlucoseUnit(units: BloodGlucoseUnits)

    fun getBloodGlucoseUnit(): Flow<BloodGlucoseUnits>

}