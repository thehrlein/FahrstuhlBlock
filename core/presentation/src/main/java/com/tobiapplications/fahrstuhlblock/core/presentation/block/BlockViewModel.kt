package com.tobiapplications.fahrstuhlblock.core.presentation.block

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tobiapplications.fahrstuhlblock.core.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.core.presentation.general.BaseToolbarViewModel

class BlockViewModel(
    gameId: Long
) : BaseToolbarViewModel() {

    private val _gameId = MutableLiveData(gameId)
    val gameId: LiveData<Long> = _gameId

    fun openMenu() {
        navigateTo(Screen.Block.Menu)
    }
}
