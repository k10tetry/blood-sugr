package com.k10tetry.bloodsugr.di.module

import com.k10tetry.bloodsugr.common.SugrDispatcher
import com.k10tetry.bloodsugr.common.SugrDispatcherImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSugrDispatcher(): SugrDispatcher = SugrDispatcherImpl()
}