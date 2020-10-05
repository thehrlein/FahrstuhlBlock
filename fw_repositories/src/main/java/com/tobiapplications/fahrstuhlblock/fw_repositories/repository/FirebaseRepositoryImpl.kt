package com.tobiapplications.fahrstuhlblock.fw_repositories.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.interactor.datasource.firebase.AnalyticsDatasource
import com.tobiapplications.fahrstuhlblock.interactor.repository.FirebaseRepository

class FirebaseRepositoryImpl(
    private val analyticsDatasource: AnalyticsDatasource
) : FirebaseRepository {

    override suspend fun trackEvent(analyticsEvent: AnalyticsEvent): AppResult<Unit> {
        return analyticsDatasource.trackEvent(analyticsEvent)
    }
}
