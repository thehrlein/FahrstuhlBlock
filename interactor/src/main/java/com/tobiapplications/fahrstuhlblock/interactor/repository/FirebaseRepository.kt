package com.tobiapplications.fahrstuhlblock.interactor.repository

import android.os.Bundle
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent

interface FirebaseRepository {

    suspend fun trackEvent(analyticsEvent: AnalyticsEvent) : AppResult<Unit>
}