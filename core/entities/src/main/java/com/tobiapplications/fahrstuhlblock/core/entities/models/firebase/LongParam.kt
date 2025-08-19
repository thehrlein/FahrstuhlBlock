package com.tobiapplications.fahrstuhlblock.core.entities.models.firebase

data class LongParam(
    override var name: String,
    val value: Long
) : AnalyticsParam
