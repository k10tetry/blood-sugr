package com.k10tetry.bloodsugr.di.module

import com.k10tetry.bloodsugr.common.SugrDispatcher
import com.k10tetry.bloodsugr.common.SugrDispatcherImpl
import com.k10tetry.bloodsugr.data.local.database.dao.SugrDao
import com.k10tetry.bloodsugr.data.local.datastore.SugrDataStore
import com.k10tetry.bloodsugr.data.local.repository.SugrLocalRepositoryImpl
import com.k10tetry.bloodsugr.domain.repository.LocalRepository
import com.k10tetry.bloodsugr.domain.usecase.ConvertBloodGlucoseUseCase
import com.k10tetry.bloodsugr.domain.usecase.GetAverageBloodGlucoseUseCase
import com.k10tetry.bloodsugr.domain.usecase.GetBloodGlucoseListUseCase
import com.k10tetry.bloodsugr.domain.usecase.GetBloodGlucoseUnitUseCase
import com.k10tetry.bloodsugr.domain.usecase.MainUseCase
import com.k10tetry.bloodsugr.domain.usecase.SaveBloodGlucoseUnitUseCase
import com.k10tetry.bloodsugr.domain.usecase.SaveBloodGlucoseUseCase
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
        sugrDao: SugrDao, sugrDataStore: SugrDataStore, sugrDispatcher: SugrDispatcher
    ): LocalRepository = SugrLocalRepositoryImpl(sugrDao, sugrDataStore, sugrDispatcher)

    @Provides
    @Singleton
    fun provideMainUseCase(
        saveUnit: SaveBloodGlucoseUnitUseCase,
        getUnit: GetBloodGlucoseUnitUseCase,
        getAverage: GetAverageBloodGlucoseUseCase,
        convert: ConvertBloodGlucoseUseCase,
        saveBloodGlucose: SaveBloodGlucoseUseCase,
        getBloodGlucoseList: GetBloodGlucoseListUseCase
    ): MainUseCase {
        return MainUseCase(
            saveUnit, getUnit, getAverage, convert, saveBloodGlucose, getBloodGlucoseList
        )
    }
}