package com.tobiapplications.fahrstuhlblock.interactor.datasource.firebase

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent

interface AnalyticsDatasource {

    suspend fun trackEvent(analyticsEvent: AnalyticsEvent): AppResult<Unit>
}
