package com.tobiapplications.fahrstuhlblock.presentation.general

import androidx.lifecycle.ViewModel
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.presentation.SingleLiveEvent


typealias NavigationCommand = SingleLiveEvent<Screen>

abstract class BaseViewModel : ViewModel() {

    val navigationEvent = NavigationCommand()

    fun navigateTo(screen: Screen) {
        navigationEvent.postValue(screen)
    }

}