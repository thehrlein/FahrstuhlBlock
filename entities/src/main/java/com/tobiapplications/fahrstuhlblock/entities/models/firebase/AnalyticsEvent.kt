package com.tobiapplications.fahrstuhlblock.entities.models.firebase

data class AnalyticsEvent(
    val eventName: String,
    val params: List<AnalyticsParam> = emptyList()
)