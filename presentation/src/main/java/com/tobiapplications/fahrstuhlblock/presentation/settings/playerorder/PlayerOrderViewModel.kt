package com.tobiapplications.fahrstuhlblock.presentation.settings.playerorder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel

class PlayerOrderViewModel(
    playerSettingsData: PlayerSettingsData
) : BaseViewModel(), PlayerOrderInteractions {

    private val _playerNames = MutableLiveData(playerSettingsData.names)
    val playerNames: LiveData<List<String>> = _playerNames

    override fun onPlayerOrderChanged(names: List<String>) {
        _playerNames.postValue(names)
    }

    fun onProceedClicked() {
        val orderedNames = playerNames.value ?: return
        navigateTo(Screen.PlayerOrder.GameRules(PlayerSettingsData(orderedNames)))
    }

    fun onInfoIconClicked() {
        navigateTo(Screen.PlayerOrder.Info)
    }
}
