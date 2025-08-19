package com.tobiapplications.fahrstuhlblock.core.presentation.menu

import com.tobiapplications.fahrstuhlblock.core.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.core.presentation.general.BaseToolbarViewModel

class MenuViewModel : BaseToolbarViewModel() {

    fun openNewGameClicked() {
        navigateTo(Screen.Menu.NewGame)
    }

    fun continueOldGameClicked() {
        navigateTo(Screen.Menu.SavedGames)
    }
}
