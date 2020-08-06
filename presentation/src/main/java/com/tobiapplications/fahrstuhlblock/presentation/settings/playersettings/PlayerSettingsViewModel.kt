package com.tobiapplications.fahrstuhlblock.presentation.settings.playersettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerError
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.interactor.usecase.invoke
import com.tobiapplications.fahrstuhlblock.interactor.usecase.player.GetPlayerNamesUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.player.StorePlayerNamesUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

private const val DEFAULT_PLAYER_COUNT = 3

class PlayerSettingsViewModel(
    private val getPlayerNamesUseCase: GetPlayerNamesUseCase,
    private val storePlayerNamesUseCase: StorePlayerNamesUseCase
) : BaseViewModel() {

    private val _playerNameOptions = MutableLiveData<Set<String>>()
    val playerNameOptions: LiveData<Set<String>> = _playerNameOptions
    private val _playerCount = MutableLiveData<Int>()
    val playerCount: LiveData<Int> = _playerCount
    private val _inputValid = MutableLiveData<Boolean>(true)
    val inputValid: LiveData<Boolean> = _inputValid
    private val _playerNames = MutableLiveData<List<String>>()
    val playerNames: LiveData<List<String>> = _playerNames

    init {
        setPlayerCount(DEFAULT_PLAYER_COUNT)
        getPlayerNameOptionsSet()
    }

    private fun getPlayerNameOptionsSet() {
        viewModelScope.launch {
            when (val result = getPlayerNamesUseCase.invoke()) {
                is AppResult.Success -> _playerNameOptions.postValue(result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    fun setPlayerCount(playerCount: Int) {
        _playerCount.postValue(playerCount)
    }

    fun onProceedClicked(inputs: List<Pair<Int, String?>>?) {
        if (inputs != null) {
            val names = inputs.mapNotNull { it.second }
            storePlayerNames(names)
            navigateTo(Screen.PlayerSettings.PlayerOrder(PlayerSettingsData(names)))
            _playerNames.postValue(names)
        }
    }

    private fun storePlayerNames(playerNames: List<String>) {
        viewModelScope.launch {
            when (val result = storePlayerNamesUseCase.invoke(playerNames)) {
                is AppResult.Success -> Unit
                is AppResult.Error -> Unit
            }
        }
    }
}