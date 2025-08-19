package com.tobiapplications.fahrstuhlblock.core.interactor.repository

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.firebase.AnalyticsEvent

interface FirebaseRepository {

    suspend fun trackEvent(analyticsEvent: AnalyticsEvent): AppResult<Unit>
}
