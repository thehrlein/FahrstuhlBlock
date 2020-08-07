package com.tobiapplications.fahrstuhlblock.presentation.settings.gamerules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.BooleanParam
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.TrackingConstants
import com.tobiapplications.fahrstuhlblock.entities.models.settings.*
import com.tobiapplications.fahrstuhlblock.interactor.usecase.firebase.TrackAnalyticsEventUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.invoke
import com.tobiapplications.fahrstuhlblock.interactor.usecase.settings.GetLastSettingsUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.user.IsShowTrumpDialogEnabledUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.user.SetShowTrumpDialogEnabledUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

class GameRulesViewModel(
    private val playerSettingsData: PlayerSettingsData,
    private val isShowTrumpDialogEnabledUseCase: IsShowTrumpDialogEnabledUseCase,
    private val setShowTrumpDialogEnabledUseCase: SetShowTrumpDialogEnabledUseCase,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase,
    private val getLastSettingsUseCase: GetLastSettingsUseCase
) : BaseViewModel() {

    private val _maxCardCountSelection = MutableLiveData(MaxCardCountSelection.ONE_DECK)
    val maxCardCountSelection: LiveData<MaxCardCountSelection> = _maxCardCountSelection
    private val _playerCount = MutableLiveData(playerSettingsData.names.size)
    val playerCount: LiveData<Int> = _playerCount
    val individualCardCountValue = MutableLiveData<String>()
    private val _showTrumpDialogEnabled = MutableLiveData<Boolean>()
    val showTrumpDialogEnabled: LiveData<Boolean> = _showTrumpDialogEnabled

    val inputValid = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(maxCardCountSelection) {
            mediator.postValue(
                when {
                    it != MaxCardCountSelection.INDIVIDUAL -> true
                    individualCardCountValue.value.isNullOrEmpty().not() -> true
                    else -> false
                }
            )
        }
        mediator.addSource(individualCardCountValue) {
            mediator.postValue(!it.isNullOrEmpty())
        }
    }

    init {
        checkShowTrumpDialogEnabled()
        getLastSettings()
    }

    private fun checkShowTrumpDialogEnabled() {
        viewModelScope.launch {
            when (val result = isShowTrumpDialogEnabledUseCase.invoke()) {
                is AppResult.Success -> _showTrumpDialogEnabled.postValue(result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun getLastSettings() {
        viewModelScope.launch {
            when (val result = getLastSettingsUseCase.invoke(SettingsScreen.CARDS)) {
                is AppResult.Success -> setLastSettings(result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun setLastSettings(settingsData: SettingsData) {
        if (settingsData is SettingsData.Cards) {
            val maxCardCountSelection = when (settingsData) {
                is SettingsData.Cards.OneDeck -> MaxCardCountSelection.ONE_DECK
                is SettingsData.Cards.TwoDecks -> MaxCardCountSelection.TWO_DECKS
                is SettingsData.Cards.Individual -> {
                    individualCardCountValue.postValue(settingsData.count.toString())
                    MaxCardCountSelection.INDIVIDUAL
                }
            }
            setSelectedCardOption(maxCardCountSelection)
        }
    }

    fun setSelectedCardOption(maxCardCountSelection: MaxCardCountSelection) {
        _maxCardCountSelection.postValue(maxCardCountSelection)
    }

    fun onProceedClicked() {
        val selection =
            maxCardCountSelection.value ?: error("could not determine maxCardCountSelection")
        val highCardCount = getHighCardCount(selection)
        navigateTo(
            Screen.GameRules.PointRules(
                GameRuleSettingsData(
                    playerSettingsData,
                    highCardCount,
                    selection
                )
            )
        )
    }

    private fun getHighCardCount(selection: MaxCardCountSelection): Int {
        val individualCount = individualCardCountValue.value?.toIntOrNull()
        return when (selection) {
            MaxCardCountSelection.ONE_DECK -> selection.cards / playerSettingsData.names.size
            MaxCardCountSelection.TWO_DECKS -> selection.cards / playerSettingsData.names.size
            MaxCardCountSelection.INDIVIDUAL -> individualCount
                ?: error("could not determine max card count - individual count is null but selected")
            else -> error("could not determine max card count")
        }
    }

    fun onAutoShowTrumpDialogChanged(checked: Boolean) {
        if (checked == showTrumpDialogEnabled.value) return
        viewModelScope.launch {
            trackAnalyticsEventUseCase.invoke(
                AnalyticsEvent(
                    eventName = TrackingConstants.EVENT_TRUMP_SELECTION_AUTO_SHOW_DIALOG,
                    params = listOf(BooleanParam(TrackingConstants.PARAM_AUTO_SHOW_DIALOG, checked))
                )
            )
            when (val result = setShowTrumpDialogEnabledUseCase.invoke(checked)) {
                is AppResult.Success -> Unit
                is AppResult.Error -> Unit
            }
        }
    }
}