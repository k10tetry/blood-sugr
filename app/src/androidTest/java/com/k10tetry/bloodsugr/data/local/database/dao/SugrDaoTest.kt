package com.k10tetry.bloodsugr.data.local.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.k10tetry.bloodsugr.data.local.database.SugrDatabase
import com.k10tetry.bloodsugr.data.local.database.model.BloodGlucose
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SugrDaoTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var database: SugrDatabase

    private lateinit var sugrDao: SugrDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SugrDatabase::class.java,
        ).build()
        sugrDao = database.sugrDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndReadBloodGlucose() = runTest(UnconfinedTestDispatcher()) {

        sugrDao.insert(BloodGlucose(10.0, 0, 1100))
        val list = sugrDao.getBloodGlucose().first()

        Truth.assertThat(list.size).isEqualTo(1)
        Truth.assertThat(list.first().value).isEqualTo(10.0)
    }


}