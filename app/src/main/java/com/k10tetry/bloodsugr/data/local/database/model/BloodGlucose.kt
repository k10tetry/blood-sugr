package com.k10tetry.bloodsugr.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blood_glucose")
class BloodGlucose(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0L,
    @ColumnInfo("value") val value: Double,
    @ColumnInfo("unit_ordinal") val unitOrdinal: Int,
    @ColumnInfo("milli_seconds") val milliseconds: Long
)


