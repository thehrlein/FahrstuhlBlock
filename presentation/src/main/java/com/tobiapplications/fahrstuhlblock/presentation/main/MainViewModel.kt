package com.tobiapplications.fahrstuhlblock.presentation.main

import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel

class MainViewModel : BaseViewModel() {

    fun openNavigation() {
        navigateTo(Screen.Main.Menu)
    }
}