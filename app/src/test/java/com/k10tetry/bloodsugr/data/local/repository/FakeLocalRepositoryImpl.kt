package com.k10tetry.bloodsugr.data.local.repository

import com.k10tetry.bloodsugr.domain.model.BloodGlucoseModel
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import com.k10tetry.bloodsugr.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalRepositoryImpl : LocalRepository {

    private var savedBloodGlucoseUnits: BloodGlucoseUnits = BloodGlucoseUnits.MILLI_MOLES_LTR
    private var savedBloodGlucoseList: MutableList<BloodGlucoseModel> = mutableListOf()

    override suspend fun saveBloodGlucose(model: BloodGlucoseModel) {
        savedBloodGlucoseList.add(model)
    }

    override fun getBloodGlucoseList(): Flow<List<BloodGlucoseModel>> =
        flow { emit(savedBloodGlucoseList) }

    override suspend fun saveBloodGlucoseUnit(units: BloodGlucoseUnits) {
        savedBloodGlucoseUnits = units
    }

    override fun getBloodGlucoseUnit(): Flow<BloodGlucoseUnits> =
        flow { emit(savedBloodGlucoseUnits) }

}