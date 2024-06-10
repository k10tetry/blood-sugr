package com.k10tetry.bloodsugr.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.k10tetry.bloodsugr.data.local.database.dao.SugrDao
import com.k10tetry.bloodsugr.data.local.database.model.BloodGlucose

@Database(entities = [BloodGlucose::class], version = 1)
abstract class SugrDatabase : RoomDatabase() {
    abstract fun sugrDao(): SugrDao
}