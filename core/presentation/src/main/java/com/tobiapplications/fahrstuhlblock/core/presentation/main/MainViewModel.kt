package com.tobiapplications.fahrstuhlblock.core.presentation.main

import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.tobiapplications.fahrstuhlblock.core.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.core.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.firebase.TrackAnalyticsEventUseCase
import com.tobiapplications.fahrstuhlblock.core.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            trackAnalyticsEventUseCase.invoke(AnalyticsEvent(FirebaseAnalytics.Event.APP_OPEN))
        }
    }

    fun openNavigation() {
        navigateTo(Screen.Main.Menu)
    }
}
