package com.k10tetry.bloodsugr.di.module

import com.k10tetry.bloodsugr.common.SugrDispatcher
import com.k10tetry.bloodsugr.common.SugrDispatcherImpl
import com.k10tetry.bloodsugr.data.local.database.dao.SugrDao
import com.k10tetry.bloodsugr.data.local.datastore.SugrDataStore
import com.k10tetry.bloodsugr.data.local.repository.SugrLocalRepositoryImpl
import com.k10tetry.bloodsugr.domain.repository.LocalRepository
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

    @Provides
    @Singleton
    fun provideLocalRepository(
        sugrDao: SugrDao,
        sugrDataStore: SugrDataStore,
        sugrDispatcher: SugrDispatcher
    ): LocalRepository = SugrLocalRepositoryImpl(sugrDao, sugrDataStore, sugrDispatcher)
}