package com.tobiapplications.fahrstuhlblock.presentation.block.info

import androidx.lifecycle.LiveData
import com.tobiapplications.fahrstuhlblock.presentation.SingleLiveEvent
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel

class AboutViewModel : BaseViewModel() {

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
    }

    fun onWizardBlockClicked() {
        _openWizardEvent.postValue(Unit)
    }

    fun onMovieBaseClicked() {
        _openMovieBaseEvent.postValue(Unit)
    }

}