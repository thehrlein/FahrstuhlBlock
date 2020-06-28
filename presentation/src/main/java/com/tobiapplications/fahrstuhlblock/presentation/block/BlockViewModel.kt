package com.tobiapplications.fahrstuhlblock.presentation.block

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.Game
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.StoreGameUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseToolbarViewModel
import kotlinx.coroutines.launch

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

}