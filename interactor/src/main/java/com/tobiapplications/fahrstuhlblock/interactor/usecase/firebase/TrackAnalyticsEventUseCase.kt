package com.tobiapplications.fahrstuhlblock.interactor.usecase.firebase

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.interactor.repository.FirebaseRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class TrackAnalyticsEventUseCase(
    private val firebaseRepository: FirebaseRepository
) : BaseUseCase<AnalyticsEvent, Unit>() {

    override suspend fun execute(parameters: AnalyticsEvent): AppResult<Unit> {
        return firebaseRepository.trackEvent(parameters)
    }
}