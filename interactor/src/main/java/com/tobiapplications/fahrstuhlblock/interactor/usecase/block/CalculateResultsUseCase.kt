package com.tobiapplications.fahrstuhlblock.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase
import kotlin.math.abs

class CalculateResultsUseCase : BaseUseCase<CalculateResultData, List<PlayerResultData>>() {

    override suspend fun execute(parameters: CalculateResultData): AppResult<List<PlayerResultData>> {
        return if (parameters.pointsRuleData.pointsIfPredictionFalse) {
            calculateWithPointsIfPredictionIsFalse(parameters)
        } else {
            calculateWithoutPointsIfPredictionIsFalse(parameters)
        }
    }

    private fun calculateWithPointsIfPredictionIsFalse(data: CalculateResultData): AppResult<List<PlayerResultData>> {
        val pointRules = data.pointsRuleData
        val resultData = mutableListOf<PlayerResultData>()
        data.tipps.forEachIndexed { index, tipp ->
            var points = 0
            val result = data.results[index]
            points += result * pointRules.pointsPerStitch
            if (tipp == result) {
                points += pointRules.correctPoints
            }

            resultData.add(
                PlayerResultData(
                    result = result,
                    difference = points,
                    total = data.previousTotals[index] + points
                )
            )
        }

        return AppResult.Success(resultData)
    }


    private fun calculateWithoutPointsIfPredictionIsFalse(data: CalculateResultData): AppResult<List<PlayerResultData>> {
        val pointRules = data.pointsRuleData
        val resultData = mutableListOf<PlayerResultData>()
        data.tipps.forEachIndexed { index, tipp ->
            var points = 0
            val result = data.results[index]

            points += if (tipp == result) {
                result * pointRules.pointsPerStitch
            } else {
                -abs(tipp - result) * pointRules.minusPointsPerStitch
            }
            if (tipp == result) {
                points += pointRules.correctPoints
            }

            resultData.add(
                PlayerResultData(
                    result = result,
                    difference = points,
                    total = data.previousTotals[index] + points
                )
            )
        }

        return AppResult.Success(resultData)
    }
}