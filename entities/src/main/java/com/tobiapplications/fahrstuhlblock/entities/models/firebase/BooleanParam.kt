package com.tobiapplications.fahrstuhlblock.entities.models.firebase

data class BooleanParam(
    override var name: String,
    val value: Boolean
) : AnalyticsParam
