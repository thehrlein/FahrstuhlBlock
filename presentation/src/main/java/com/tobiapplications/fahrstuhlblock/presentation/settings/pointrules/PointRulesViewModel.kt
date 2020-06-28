package com.tobiapplications.fahrstuhlblock.presentation.settings.pointrules

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.FahrstuhlGame
import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel

private const val DEFAULT_CORRECT_PREDICTION_POINTS = "10"
private const val DEFAULT_POINTS_PER_STITCH = "3"
private const val DEFAULT_POINTS_IF_PREDICTION_FALSE = false

class PointRulesViewModel(
    private val gameRuleSettingsData: GameRuleSettingsData
): BaseViewModel() {

    val correctPredictionPoints = MutableLiveData(DEFAULT_CORRECT_PREDICTION_POINTS)
    val pointsPerStitchCorrect = MutableLiveData(DEFAULT_POINTS_PER_STITCH)
    val minusPointsPerStitch = MutableLiveData(DEFAULT_POINTS_PER_STITCH)
    val pointsIfPredictionFalse = MutableLiveData(DEFAULT_POINTS_IF_PREDICTION_FALSE)

    val inputValid = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(correctPredictionPoints) {
            mediator.postValue(when {
                it.isNullOrEmpty() -> false
                pointsPerStitchCorrect.value.isNullOrEmpty() -> false
                pointsIfPredictionFalse.value == false && minusPointsPerStitch.value.isNullOrEmpty() -> false
                else -> true
            })
        }
        mediator.addSource(pointsPerStitchCorrect) {
            mediator.postValue(when {
                it.isNullOrEmpty() -> false
                correctPredictionPoints.value.isNullOrEmpty() -> false
                pointsIfPredictionFalse.value == false && minusPointsPerStitch.value.isNullOrEmpty() -> false
                else -> true
            })
        }

        mediator.addSource(minusPointsPerStitch) {
            mediator.postValue(when {
                it.isNullOrEmpty() -> false
                correctPredictionPoints.value.isNullOrEmpty() -> false
                pointsPerStitchCorrect.value.isNullOrEmpty() -> false
                else -> true
            })
        }

        mediator.addSource(pointsIfPredictionFalse) {
            mediator.postValue(when {
                it == false && minusPointsPerStitch.value.isNullOrEmpty() -> false
                correctPredictionPoints.value.isNullOrEmpty() -> false
                pointsPerStitchCorrect.value.isNullOrEmpty() -> false
                else -> true
            })
        }
    }

    fun onProceedClicked() {
        val correctPredictionPoints = correctPredictionPoints.value?.toInt() ?: error("could not determine correct prediction points")
        val pointsPerStitch = pointsPerStitchCorrect.value?.toInt() ?: error("could not determine correct points per stitch")
        val minusPointsPerStitch = minusPointsPerStitch.value?.toInt() ?: error("could not determine minus points per stitch")
        val pointsIfPredictionFalse = pointsIfPredictionFalse.value ?: error("could not determine pointsIfPredictionFalse")
        navigateTo(Screen.PointRules.Block(FahrstuhlGame(gameRuleSettingsData.playerSettingsData, gameRuleSettingsData.highCardCount, PointsRuleData(correctPredictionPoints, pointsPerStitch, minusPointsPerStitch, pointsIfPredictionFalse))))
    }
}