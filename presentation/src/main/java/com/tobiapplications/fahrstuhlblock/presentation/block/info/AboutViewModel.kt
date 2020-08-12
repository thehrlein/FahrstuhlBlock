package com.tobiapplications.fahrstuhlblock.presentation.block.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.TrackingConstants
import com.tobiapplications.fahrstuhlblock.interactor.usecase.firebase.TrackAnalyticsEventUseCase
import com.tobiapplications.fahrstuhlblock.presentation.SingleLiveEvent
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

class AboutViewModel(
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase
) : BaseViewModel() {

    private val _sendEmailEvent = SingleLiveEvent<Unit>()
    val sendEmail : LiveData<Unit> = _sendEmailEvent
    private val _openFahrstuhlEvent = SingleLiveEvent<Unit>()
    val openFahrstuhl : LiveData<Unit> = _openFahrstuhlEvent
    private val _openWizardEvent = SingleLiveEvent<Unit>()
    val openWizard : LiveData<Unit> = _openWizardEvent
    private val _openMovieBaseEvent = SingleLiveEvent<Unit>()
    val openMovieBase : LiveData<Unit> = _openMovieBaseEvent


    fun onFabClicked() {
        _sendEmailEvent.postValue(Unit)
    }

    fun onFahrstuhlBlockClicked() {
        _openFahrstuhlEvent.postValue(Unit)
        trackOpening(TrackingConstants.EVENT_OPEN_PLAYSTORE_FAHRSTUHL_BLOCK)
    }

    fun onWizardBlockClicked() {
        _openWizardEvent.postValue(Unit)
        trackOpening(TrackingConstants.EVENT_OPEN_PLAYSTORE_WIZARD_BLOCK)
    }

    fun onMovieBaseClicked() {
        _openMovieBaseEvent.postValue(Unit)
        trackOpening(TrackingConstants.EVENT_OPEN_PLAYSTORE_MOVIEBASE)
    }

    private fun trackOpening(eventName: String) {
        viewModelScope.launch {
            trackAnalyticsEventUseCase.invoke(
                AnalyticsEvent(
                    eventName = eventName
                )
            )
        }
    }
}