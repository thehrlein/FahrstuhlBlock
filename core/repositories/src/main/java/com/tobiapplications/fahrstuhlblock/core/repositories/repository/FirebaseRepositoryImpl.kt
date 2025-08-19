package com.tobiapplications.fahrstuhlblock.core.repositories.repository

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.core.interactor.datasource.firebase.AnalyticsDatasource
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.FirebaseRepository

class FirebaseRepositoryImpl(
    private val analyticsDatasource: AnalyticsDatasource
) : FirebaseRepository {

    override suspend fun trackEvent(analyticsEvent: AnalyticsEvent): AppResult<Unit> {
        return analyticsDatasource.trackEvent(analyticsEvent)
    }
}
