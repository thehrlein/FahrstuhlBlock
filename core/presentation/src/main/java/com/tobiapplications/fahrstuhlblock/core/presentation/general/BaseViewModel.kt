package com.tobiapplications.fahrstuhlblock.core.presentation.general

import androidx.lifecycle.ViewModel
import com.tobiapplications.fahrstuhlblock.core.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.core.presentation.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    val navigationEvent = SingleLiveEvent<Screen>()

    fun navigateTo(screen: Screen) {
        navigationEvent.postValue(screen)
    }
}
