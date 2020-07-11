package com.tobiapplications.fahrstuhlblock.entities.models.firebase

data class StringParam(
    override var name: String,
    val value: String
) : AnalyticsParam