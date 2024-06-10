package com.k10tetry.bloodsugr.domain.model

data class BloodGlucoseModel(
    val id: Long = 0L,
    val value: Double,
    val units: BloodGlucoseUnits,
    val milliseconds: Long
)