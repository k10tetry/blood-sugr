package com.k10tetry.bloodsugr.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.k10tetry.bloodsugr.data.local.database.model.BloodGlucose
import kotlinx.coroutines.flow.Flow

@Dao
interface SugrDao {

    @Insert
    suspend fun insert(bloodGlucose: BloodGlucose)

    @Query("SELECT * FROM blood_glucose")
    fun getBloodGlucose(): Flow<List<BloodGlucose>>

}