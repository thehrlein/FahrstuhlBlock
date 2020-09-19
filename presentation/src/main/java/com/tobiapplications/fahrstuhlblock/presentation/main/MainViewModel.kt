package com.tobiapplications.fahrstuhlblock.presentation.main

import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.interactor.usecase.firebase.TrackAnalyticsEventUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
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
