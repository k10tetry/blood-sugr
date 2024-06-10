package com.k10tetry.bloodsugr.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SugrDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val unitPreferenceKey = intPreferencesKey(BLOOD_GLUCOSE_UNIT)

    companion object {
        const val BLOOD_GLUCOSE_UNIT = "blood_glucose_unit"
    }

    suspend fun saveBloodGlucoseUnit(ordinal: Int) {
        dataStore.edit { pref ->
            pref[unitPreferenceKey] = ordinal
        }
    }

    fun getBloodGlucoseUnit(): Flow<Int> {
        return dataStore.data.map {
            it[unitPreferenceKey] ?: 0
        }
    }


}