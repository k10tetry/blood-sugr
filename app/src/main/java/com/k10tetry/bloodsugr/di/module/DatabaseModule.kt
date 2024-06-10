package com.k10tetry.bloodsugr.di.module

import android.content.Context
import androidx.room.Room
import com.k10tetry.bloodsugr.data.local.database.SugrDatabase
import com.k10tetry.bloodsugr.data.local.database.dao.SugrDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSugrDatabase(@ApplicationContext context: Context): SugrDatabase =
        Room.databaseBuilder(context, SugrDatabase::class.java, "sugr_database").build()

    @Provides
    @Singleton
    fun provideSugrDao(sugrDatabase: SugrDatabase): SugrDao = sugrDatabase.sugrDao()

}