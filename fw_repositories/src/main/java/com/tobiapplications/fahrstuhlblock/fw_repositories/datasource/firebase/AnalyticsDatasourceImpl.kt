package com.tobiapplications.fahrstuhlblock.fw_repositories.datasource.firebase

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.*
import com.tobiapplications.fahrstuhlblock.interactor.datasource.firebase.AnalyticsDatasource

class AnalyticsDatasourceImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsDatasource {

    override suspend fun trackEvent(analyticsEvent: AnalyticsEvent) : AppResult<Unit> {
        firebaseAnalytics.logEvent(analyticsEvent.eventName) {
            analyticsEvent.params.forEach {
                when (it) {
                    is StringParam -> param(it.name, it.value)
                    is DoubleParam -> param(it.name, it.value)
                    is LongParam -> param(it.name, it.value)
                    is IntParam -> param(it.name, it.value.toLong())
                    is BooleanParam -> param(it.name, it.value.toString())
                }
            }
        }
        return AppResult.Success(Unit)
    }
}