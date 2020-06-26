package com.tobiapplications.fahrstuhlblock.presentation.settings.playersettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
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

    private val _players = MutableLiveData(
        mutableListOf<Pair<Boolean, MutableLiveData<String>>>(
            Pair(false, MutableLiveData("a")), // TODO remove initial data
            Pair(false, MutableLiveData("b")),
            Pair(false, MutableLiveData("c")),
            Pair(false, MutableLiveData("")),
            Pair(false, MutableLiveData("")),
            Pair(false, MutableLiveData("")),
            Pair(false, MutableLiveData("")),
            Pair(false, MutableLiveData(""))
        )
    )

    val players: LiveData<MutableList<Pair<Boolean, MutableLiveData<String>>>> = _players

    private val _playerNameOptions = MutableLiveData<List<String>>()
    val playerNameOptions: LiveData<List<String>> = _playerNameOptions

    val inputValid = MediatorLiveData<Boolean>().also { mediator ->
        players.value?.forEach { player ->
            mediator.addSource(player.second) {
                var inputsCorrect = true
                players.value?.forEach { player ->
                    if (player.first && player.second.value.isNullOrEmpty()) {
                        inputsCorrect = false
                    }
                }

                mediator.postValue(inputsCorrect)
            }
        }
    }

    init {
        setPlayerCount(DEFAULT_PLAYER_COUNT)
        getPlayerNameOptions()
    }

    private fun getPlayerNameOptions() {
        viewModelScope.launch {
            when (val result = getPlayerNamesUseCase.invoke()) {
                is AppResult.Success -> _playerNameOptions.postValue(result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    /**
     * 1-Indexed count
     */
    fun setPlayerCount(playerCount: Int) {
        val playerSize = players.value?.size ?: 0
        val list = players.value ?: return
        for (index in 0 until playerSize) {
            list[index] = list[index].copy(first = index < playerCount)
            _players.postValue(list)
        }
    }

    fun onProceedClicked() {
        val playerNames = players.value
            ?.filter { it.first }
            ?.mapNotNull { it.second.value } ?: emptyList()
        storePlayerNames(playerNames)

        navigateTo(Screen.PlayerSettings.PlayerOrder(PlayerSettingsData(playerNames)))
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