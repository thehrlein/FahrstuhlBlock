package com.tobiapplications.fahrstuhlblock.core.entities.models.firebase

data class BooleanParam(
    override var name: String,
    val value: Boolean
) : AnalyticsParam
