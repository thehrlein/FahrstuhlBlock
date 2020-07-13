package com.tobiapplications.fahrstuhlblock.presentation.general

import androidx.lifecycle.ViewModel
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.presentation.SingleLiveEvent


abstract class BaseViewModel : ViewModel() {

    val navigationEvent = SingleLiveEvent<Screen>()

    fun navigateTo(screen: Screen) {
        navigationEvent.postValue(screen)
    }

}