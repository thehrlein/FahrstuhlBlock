package com.tobiapplications.fahrstuhlblock.core.interactor.datasource.firebase

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.firebase.AnalyticsEvent

interface AnalyticsDatasource {

    suspend fun trackEvent(analyticsEvent: AnalyticsEvent): AppResult<Unit>
}
