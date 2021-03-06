package com.tobiapplications.fahrstuhlblock.presentation.settings.pointrules

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.TrackingConstants
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.GameInfo
import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.SettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.SettingsScreen
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.StoreGameInfoUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.firebase.TrackAnalyticsEventUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.settings.GetLastSettingsUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

private const val DEFAULT_CORRECT_PREDICTION_POINTS = "10"
private const val DEFAULT_POINTS_PER_STITCH = "3"
private const val DEFAULT_POINTS_IF_PREDICTION_FALSE = false

class PointRulesViewModel(
    private val gameRuleSettingsData: GameRuleSettingsData,
    private val storeGameInfoUseCase: StoreGameInfoUseCase,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase,
    private val getLastSettingsUseCase: GetLastSettingsUseCase
) : BaseViewModel() {

    val correctPredictionPoints = MutableLiveData(DEFAULT_CORRECT_PREDICTION_POINTS)
    val pointsPerStitchCorrect = MutableLiveData(DEFAULT_POINTS_PER_STITCH)
    val minusPointsPerStitch = MutableLiveData(DEFAULT_POINTS_PER_STITCH)
    val pointsIfPredictionFalse = MutableLiveData(DEFAULT_POINTS_IF_PREDICTION_FALSE)
    val inputValid = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(correctPredictionPoints) {
            mediator.postValue(
                when {
                    it.isNullOrEmpty() -> false
                    pointsPerStitchCorrect.value.isNullOrEmpty() -> false
                    pointsIfPredictionFalse.value == false && minusPointsPerStitch.value.isNullOrEmpty() -> false
                    else -> true
                }
            )
        }
        mediator.addSource(pointsPerStitchCorrect) {
            mediator.postValue(
                when {
                    it.isNullOrEmpty() -> false
                    correctPredictionPoints.value.isNullOrEmpty() -> false
                    pointsIfPredictionFalse.value == false && minusPointsPerStitch.value.isNullOrEmpty() -> false
                    else -> true
                }
            )
        }

        mediator.addSource(minusPointsPerStitch) {
            mediator.postValue(
                when {
                    it.isNullOrEmpty() -> false
                    correctPredictionPoints.value.isNullOrEmpty() -> false
                    pointsPerStitchCorrect.value.isNullOrEmpty() -> false
                    else -> true
                }
            )
        }

        mediator.addSource(pointsIfPredictionFalse) {
            mediator.postValue(
                when {
                    it == false && minusPointsPerStitch.value.isNullOrEmpty() -> false
                    correctPredictionPoints.value.isNullOrEmpty() -> false
                    pointsPerStitchCorrect.value.isNullOrEmpty() -> false
                    else -> true
                }
            )
        }
    }

    init {
        getLastSettings()
    }

    private fun getLastSettings() {
        viewModelScope.launch {
            when (val result = getLastSettingsUseCase.invoke(SettingsScreen.POINTS)) {
                is AppResult.Success -> setLastSettings(result.value)
                is AppResult.Error -> {
                    correctPredictionPoints.postValue(DEFAULT_CORRECT_PREDICTION_POINTS)
                    pointsPerStitchCorrect.postValue(DEFAULT_POINTS_PER_STITCH)
                    minusPointsPerStitch.postValue(DEFAULT_POINTS_PER_STITCH)
                    pointsIfPredictionFalse.postValue(DEFAULT_POINTS_IF_PREDICTION_FALSE)
                }
            }
        }
    }

    private fun setLastSettings(settingsData: SettingsData) {
        if (settingsData is SettingsData.Points) {
            val rules = settingsData.pointsRuleData
            correctPredictionPoints.postValue(rules.correctPoints.toString())
            pointsPerStitchCorrect.postValue(rules.pointsPerStitch.toString())
            minusPointsPerStitch.postValue(rules.minusPointsPerStitch.toString())
            pointsIfPredictionFalse.postValue(rules.pointsIfPredictionFalse)
        }
    }

    fun onProceedClicked() {
        val correctPredictionPoints = correctPredictionPoints.value?.toInt()
            ?: error("could not determine correct prediction points")
        val pointsPerStitch = pointsPerStitchCorrect.value?.toInt()
            ?: error("could not determine correct points per stitch")
        val minusPointsPerStitch = minusPointsPerStitch.value?.toInt()
            ?: error("could not determine minus points per stitch")
        val pointsIfPredictionFalse =
            pointsIfPredictionFalse.value ?: error("could not determine pointsIfPredictionFalse")

        val gameInfo = GameInfo(
            0,
            System.currentTimeMillis(),
            gameRuleSettingsData.playerSettingsData,
            gameRuleSettingsData.highCardCount,
            gameRuleSettingsData.totalRounds,
            gameRuleSettingsData.stopElevatorAtHighCard,
            gameRuleSettingsData.firstRoundTipsCanBeOne,
            gameRuleSettingsData.maxCardCountSelection,
            PointsRuleData(
                correctPredictionPoints,
                pointsPerStitch,
                minusPointsPerStitch,
                pointsIfPredictionFalse
            )
        )

        viewModelScope.launch {
            trackPlayerCount(gameRuleSettingsData.playerSettingsData.names.size)
            trackHighCardCount(gameRuleSettingsData.highCardCount)
            trackPointRules(correctPredictionPoints, pointsPerStitch, minusPointsPerStitch, pointsIfPredictionFalse)
            when (val result = storeGameInfoUseCase.invoke(gameInfo)) {
                is AppResult.Success -> navigateTo(Screen.PointRules.Block(result.value))
                is AppResult.Error -> Unit
            }
        }
    }

    private suspend fun trackPlayerCount(playerCount: Int) {
        trackAnalyticsEventUseCase.invoke(
            AnalyticsEvent(
                eventName = TrackingConstants.getPlayerSettingsEvent(playerCount)
            )
        )
    }

    private suspend fun trackHighCardCount(highCardCount: Int) {
        trackAnalyticsEventUseCase.invoke(
            AnalyticsEvent(
                eventName = TrackingConstants.getGameRulesHighCardEvent(highCardCount)
            )
        )
    }

    private suspend fun trackPointRules(
        correctPredictionPoints: Int,
        pointsPerStitch: Int,
        minusPointsPerStitch: Int,
        pointsIfPredictionFalse: Boolean
    ) {
        trackAnalyticsEventUseCase.invoke(
            AnalyticsEvent(
                eventName = TrackingConstants.getPointRulesCorrectPredictionEvent(correctPredictionPoints)
            )
        )
        trackAnalyticsEventUseCase.invoke(
            AnalyticsEvent(
                eventName = TrackingConstants.getPointRulesPlusPointsEvent(pointsPerStitch)
            )
        )
        trackAnalyticsEventUseCase.invoke(
            AnalyticsEvent(
                eventName = TrackingConstants.getPointRulesMinusPointsEvent(minusPointsPerStitch)
            )
        )
        trackAnalyticsEventUseCase.invoke(
            AnalyticsEvent(
                eventName = TrackingConstants.getPointRulesPointsForWrongPredictionEvent(pointsIfPredictionFalse)
            )
        )
    }

    fun onInfoIconClicked() {
        navigateTo(Screen.PointRules.Info)
    }
}
