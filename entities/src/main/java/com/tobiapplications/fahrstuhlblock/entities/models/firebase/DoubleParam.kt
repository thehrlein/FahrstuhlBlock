package com.tobiapplications.fahrstuhlblock.entities.models.firebase

data class DoubleParam(
    override var name: String,
    val value: Double
) : AnalyticsParam
