package com.k10tetry.bloodsugr.domain.usecase

data class MainUseCase(
    val saveUnit: SaveBloodGlucoseUnitUseCase,
    val getUnit: GetBloodGlucoseUnitUseCase,
    val getAverage: GetAverageBloodGlucoseUseCase,
    val convert: ConvertBloodGlucoseUseCase,
    val saveBloodGlucose: SaveBloodGlucoseUseCase,
    val getBloodGlucoseList: GetBloodGlucoseListUseCase
)
