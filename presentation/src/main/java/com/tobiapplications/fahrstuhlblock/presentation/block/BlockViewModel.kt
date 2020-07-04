package com.tobiapplications.fahrstuhlblock.presentation.block

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseToolbarViewModel

class BlockViewModel(
    gameId: Long
) : BaseToolbarViewModel() {

    private val _gameId = MutableLiveData(gameId)
    val gameId: LiveData<Long> = _gameId

    fun showExitDialog() {
        navigateTo(Screen.Block.Exit)
    }

    fun openMenu() {
        navigateTo(Screen.Block.Menu)
    }

    fun openInput() {
        val gameId = gameId.value ?: error("could not determine gameId")
        navigateTo(Screen.Block.Input(gameId))
    }

}