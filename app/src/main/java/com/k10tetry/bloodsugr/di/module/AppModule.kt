package com.k10tetry.bloodsugr.di.module

import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // TODO: Return Room Database
    @Provides
    @Singleton
    fun provideRoomDatabase(): RoomDatabase? {
        return null
    }

}