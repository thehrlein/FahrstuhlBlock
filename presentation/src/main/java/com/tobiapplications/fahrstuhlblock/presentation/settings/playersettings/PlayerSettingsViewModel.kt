package com.tobiapplications.fahrstuhlblock.presentation.settings.playersettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.IntParam
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.LongParam
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.TrackingConstants
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerError
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.interactor.usecase.firebase.TrackAnalyticsEventUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.invoke
import com.tobiapplications.fahrstuhlblock.interactor.usecase.player.GetPlayerNamesUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.player.StorePlayerNamesUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

private const val DEFAULT_PLAYER_COUNT = 3
private const val DEFAULT_PLAYER_NAME_OCCURRENCE = 0

class PlayerSettingsViewModel(
    private val getPlayerNamesUseCase: GetPlayerNamesUseCase,
    private val storePlayerNamesUseCase: StorePlayerNamesUseCase,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase
) : BaseViewModel() {

    val playerVisibilities = MutableLiveData(
        listOf(
            MutableLiveData(true),
            MutableLiveData(true),
            MutableLiveData(true),
            MutableLiveData(false),
            MutableLiveData(false),
            MutableLiveData(false),
            MutableLiveData(false),
            MutableLiveData(false)
        )
    )

    val playerNames = MutableLiveData(
        listOf(
            MutableLiveData("a"),
            MutableLiveData("b"),
            MutableLiveData("c"),
            MutableLiveData("d"),
            MutableLiveData("e"),
            MutableLiveData("f"),
            MutableLiveData("g"),
            MutableLiveData("h")
        )
    )

    val playerErrors = MutableLiveData(
        listOf(
            MutableLiveData<PlayerError>(null),
            MutableLiveData<PlayerError>(null),
            MutableLiveData<PlayerError>(null),
            MutableLiveData<PlayerError>(null),
            MutableLiveData<PlayerError>(null),
            MutableLiveData<PlayerError>(null),
            MutableLiveData<PlayerError>(null),
            MutableLiveData<PlayerError>(null)
        )
    )

    private val _playerNameOptions = MutableLiveData<Set<String>>()
    val playerNameOptions: LiveData<Set<String>> = _playerNameOptions

    val inputValid = MediatorLiveData<Boolean>().also { mediator ->
        val players = playerNames.value ?: return@also
        mediator.addSource(players[0]) {
            mediator.checkIndividual(it, 0)
            mediator.checkRest(0)
        }
        mediator.addSource(players[1]) {
            mediator.checkIndividual(it, 1)
            mediator.checkRest(1)
        }
        mediator.addSource(players[2]) {
            mediator.checkIndividual(it, 2)
            mediator.checkRest(2)
        }
        mediator.addSource(players[3]) {
            mediator.checkIndividual(it, 3)
            mediator.checkRest(3)
        }
        mediator.addSource(players[4]) {
            val occurrences = getAllPlayerNames()[it] ?: DEFAULT_PLAYER_NAME_OCCURRENCE
            val error = when {
                it.isNullOrEmpty() -> PlayerError.EMPTY
                occurrences > 1 -> PlayerError.DUPLICATE
                else -> null
            }

            playerErrors.value?.get(4)?.postValue(error)
            mediator.postValue(
                checkInputValid(
                    error = error,
                    excludeIndex = 4
                )
            )
        }
        mediator.addSource(players[5]) {
            val occurrences = getAllPlayerNames()[it] ?: DEFAULT_PLAYER_NAME_OCCURRENCE
            val error = when {
                it.isNullOrEmpty() -> PlayerError.EMPTY
                occurrences > 1 -> PlayerError.DUPLICATE
                else -> null
            }

            playerErrors.value?.get(5)?.postValue(error)
            mediator.postValue(
                checkInputValid(
                    error = error,
                    excludeIndex = 5
                )
            )
        }
        mediator.addSource(players[6]) {
            val occurrences = getAllPlayerNames()[it] ?: DEFAULT_PLAYER_NAME_OCCURRENCE
            val error = when {
                it.isNullOrEmpty() -> PlayerError.EMPTY
                occurrences > 1 -> PlayerError.DUPLICATE
                else -> null
            }

            playerErrors.value?.get(6)?.postValue(error)
            mediator.postValue(
                checkInputValid(
                    error = error,
                    excludeIndex = 6
                )
            )
        }
        mediator.addSource(players[7]) {
            val occurrences = getAllPlayerNames()[it] ?: DEFAULT_PLAYER_NAME_OCCURRENCE
            val error = when {
                it.isNullOrEmpty() -> PlayerError.EMPTY
                occurrences > 1 -> PlayerError.DUPLICATE
                else -> null
            }

            playerErrors.value?.get(7)?.postValue(error)
            mediator.postValue(
                checkInputValid(
                    error = error,
                    excludeIndex = 7
                )
            )
        }
    }

    private fun MediatorLiveData<Boolean>.checkRest(individualIndex: Int) {
        val visibilities = playerVisibilities.value?.map { it.value } ?: emptyList()
        val errors = playerErrors.value?.map { it.value } ?: emptyList()
        visibilities.forEachIndexed { index, data ->
            if (data == true && individualIndex != index && errors[index] != null) {
                checkIndividual(playerNames.value?.get(index)?.value, index)
            }
        }
    }

    private fun MediatorLiveData<Boolean>.checkIndividual(it: String?, index: Int) {
        val occurrences = getAllPlayerNames()[it] ?: DEFAULT_PLAYER_NAME_OCCURRENCE
        val error = when {
            it.isNullOrEmpty() -> PlayerError.EMPTY
            occurrences > 1 -> PlayerError.DUPLICATE
            else -> null
        }

        playerErrors.value?.get(index)?.postValue(error)
        postValue(checkInputValid(error = error, excludeIndex = index))
    }

    private fun getAllPlayerNames(): Map<String?, Int> {
        return playerNames.value
            ?.filterIndexed { index, _ -> playerVisibilities.value?.get(index)?.value == true }
            ?.groupBy { it.value }
            ?.mapValues { it.value.size } ?: emptyMap()
    }

    private fun checkInputValid(error: PlayerError?, excludeIndex: Int): Boolean {
        val errors =
            playerErrors.value?.filterIndexed { index, mutableLiveData -> index != excludeIndex }
                ?.map { it.value }
                ?.toMutableList() ?: mutableListOf()
        errors.add(error)
        return errors.firstOrNull { it != null } == null
    }

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
        val visibilities = playerVisibilities.value ?: emptyList()
        for (index in visibilities.indices) {
            visibilities[index].postValue(index < playerCount)
        }
        playerVisibilities.postValue(visibilities)
    }

    fun onProceedClicked() {
        val visibilities = playerVisibilities.value ?: emptyList()
        val names = playerNames.value
            ?.filterIndexed { index, _ -> visibilities[index].value == true }
            ?.mapNotNull { it.value } ?: emptyList()
        storePlayerNames(names)

        viewModelScope.launch {
            trackAnalyticsEventUseCase.invoke(
                AnalyticsEvent(
                    eventName = TrackingConstants.EVENT_PLAYER_SETTINGS_PLAYER_COUNT,
                    params = listOf(IntParam(TrackingConstants.PARAM_PLAYER_COUNT, names.size))
                )
            )
            navigateTo(Screen.PlayerSettings.PlayerOrder(PlayerSettingsData(names)))
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