package com.tobiapplications.fahrstuhlblock.entities.models.firebase

data class IntParam(
    override var name: String,
    val value: Int
) : AnalyticsParam
