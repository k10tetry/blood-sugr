package com.k10tetry.bloodsugr.domain.model

data class BloodGlucoseModel(
    val value: Double,
    val units: BloodGlucoseUnits,
    val milliseconds: Long,
    val id: Long = 0L
)