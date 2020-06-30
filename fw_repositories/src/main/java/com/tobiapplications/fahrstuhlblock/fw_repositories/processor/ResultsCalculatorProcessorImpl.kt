package com.tobiapplications.fahrstuhlblock.fw_repositories.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.interactor.processor.ResultsCalculatorProcessor
import kotlin.math.abs

class ResultsCalculatorProcessorImpl : BaseProcessor, ResultsCalculatorProcessor {

    override suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>> =
        safeCall {
            if (calculateResultData.pointsRuleData.pointsIfPredictionFalse) {
                calculateWithPointsIfPredictionIsFalse(calculateResultData)
            } else {
                calculateWithoutPointsIfPredictionIsFalse(calculateResultData)
            }
        }

    private fun calculateWithPointsIfPredictionIsFalse(data: CalculateResultData): List<PlayerResultData> {
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

        return resultData
    }


    private fun calculateWithoutPointsIfPredictionIsFalse(data: CalculateResultData): List<PlayerResultData> {
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

        return resultData
    }
}