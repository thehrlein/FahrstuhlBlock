package com.tobiapplications.fahrstuhlblock.presentation.settings

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.FahrstuhlGame
import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel

private const val DEFAULT_CORRECT_PREDICTION_POINTS = "10"
private const val DEFAULT_POINTS_PER_STITCH = "3"

class PointRulesViewModel(
    private val gameRuleSettingsData: GameRuleSettingsData
): BaseViewModel() {

    val correctPredictionPoints = MutableLiveData(DEFAULT_CORRECT_PREDICTION_POINTS)
    val pointsPerStitch = MutableLiveData(DEFAULT_POINTS_PER_STITCH)

    val inputValid = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(correctPredictionPoints) {
            mediator.postValue(when {
                it.isNullOrEmpty() -> false
                pointsPerStitch.value.isNullOrEmpty() -> false
                else -> true
            })
        }
        mediator.addSource(pointsPerStitch) {
            mediator.postValue(when {
                it.isNullOrEmpty() -> false
                correctPredictionPoints.value.isNullOrEmpty() -> false
                else -> true
            })
        }
    }

    fun onProceedClicked() {
        val correctPredictionPoints = correctPredictionPoints.value?.toInt() ?: error("could not determine correct prediction points")
        val pointsPerStitch = pointsPerStitch.value?.toInt() ?: error("could not determine correct points per stitch")
        navigateTo(Screen.PointRules.Block(FahrstuhlGame(gameRuleSettingsData, PointsRuleData(correctPredictionPoints, pointsPerStitch))))
    }
}