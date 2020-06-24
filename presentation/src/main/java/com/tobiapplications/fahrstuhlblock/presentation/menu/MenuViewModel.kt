package com.tobiapplications.fahrstuhlblock.presentation.menu

import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseToolbarViewModel

class MenuViewModel : BaseToolbarViewModel() {

    fun openNewGameClicked() {
        navigateTo(Screen.Menu.NewGame)
    }
}