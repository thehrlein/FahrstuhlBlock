package com.tobiapplications.fahrstuhlblock.presentation.block

import androidx.lifecycle.MutableLiveData
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.FahrstuhlGame
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseToolbarViewModel

class BlockViewModel(
    fahrstuhlGame: FahrstuhlGame
) : BaseToolbarViewModel() {

    private val names = MutableLiveData(fahrstuhlGame.playerSettingsData.names)

    fun showExitDialog() {
        navigateTo(Screen.Block.Exit)
    }

    fun openMenu() {
        navigateTo(Screen.Block.Menu)
    }

}