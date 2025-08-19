package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.firebase

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.FirebaseRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class TrackAnalyticsEventUseCase(
    private val firebaseRepository: FirebaseRepository
) : BaseUseCase<AnalyticsEvent, Unit>() {

    override suspend fun execute(parameters: AnalyticsEvent): AppResult<Unit> {
        return firebaseRepository.trackEvent(parameters)
    }
}
