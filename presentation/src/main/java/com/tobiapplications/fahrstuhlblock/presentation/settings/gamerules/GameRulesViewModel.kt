package com.tobiapplications.fahrstuhlblock.presentation.settings.gamerules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.MaxCardCountSelection
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel

class GameRulesViewModel(
    private val playerSettingsData: PlayerSettingsData
) : BaseViewModel() {

    private val _maxCardCountSelection = MutableLiveData(MaxCardCountSelection.ONE_DECK)
    val maxCardCountSelection: LiveData<MaxCardCountSelection> = _maxCardCountSelection

    private val _playerCount = MutableLiveData(playerSettingsData.names.size)
    val playerCount: LiveData<Int> = _playerCount
    val individualCardCountValue = MutableLiveData<String>()

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

    fun setSelectedCardOption(maxCardCountSelection: MaxCardCountSelection) {
        _maxCardCountSelection.postValue(maxCardCountSelection)
    }

    fun onProceedClicked() {
        val highCardCound = getHighCardCound()
        navigateTo(Screen.GameRules.PointRules(GameRuleSettingsData(playerSettingsData, highCardCound)))
    }

    private fun getHighCardCound(): Int {
        val selection = maxCardCountSelection.value
        val individualCount = individualCardCountValue.value?.toIntOrNull()
        val cardCount = when (selection) {
            MaxCardCountSelection.ONE_DECK -> selection.cards
            MaxCardCountSelection.TWO_DECKS -> selection.cards
            MaxCardCountSelection.INDIVIDUAL -> individualCount
                ?: error("could not determine max card count - individual count is null but selected")
            else -> error("could not determine max card count")
        }

        return cardCount / playerSettingsData.names.size
    }
}