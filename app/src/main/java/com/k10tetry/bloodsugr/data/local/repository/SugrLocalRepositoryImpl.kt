package com.k10tetry.bloodsugr.data.local.repository

import com.k10tetry.bloodsugr.data.local.database.dao.SugrDao
import com.k10tetry.bloodsugr.data.local.database.model.BloodGlucose
import com.k10tetry.bloodsugr.data.local.datastore.SugrDataStore
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseModel
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import com.k10tetry.bloodsugr.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SugrLocalRepositoryImpl @Inject constructor(
    private val sugrDao: SugrDao,
    private val sugrDataStore: SugrDataStore
) : LocalRepository {

    override suspend fun saveBloodGlucose(model: BloodGlucoseModel) = sugrDao.insert(
        BloodGlucose(
            uid = model.id,
            value = model.value,
            unitOrdinal = model.units.ordinal,
            milliseconds = model.milliseconds
        )
    )

    override fun getBloodGlucoseList(): Flow<List<BloodGlucoseModel>> =
        sugrDao.getBloodGlucose().map { bloodGlucose ->
            bloodGlucose.map {
                BloodGlucoseModel(
                    id = it.uid,
                    value = it.value,
                    units = BloodGlucoseUnits.entries[it.unitOrdinal],
                    milliseconds = it.milliseconds
                )
            }
        }

    override suspend fun saveBloodGlucoseUnit(units: BloodGlucoseUnits) =
        sugrDataStore.saveBloodGlucoseUnit(units.ordinal)

    override fun getBloodGlucoseUnit(): Flow<BloodGlucoseUnits> =
        sugrDataStore.getBloodGlucoseUnit().map { BloodGlucoseUnits.entries[it] }
}
