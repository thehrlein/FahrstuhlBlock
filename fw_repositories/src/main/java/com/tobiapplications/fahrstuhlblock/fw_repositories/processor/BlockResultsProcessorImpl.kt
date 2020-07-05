package com.tobiapplications.fahrstuhlblock.fw_repositories.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerTippData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.*
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockResultsProcessor
import kotlin.math.abs

class BlockResultsProcessorImpl : BaseProcessor, BlockResultsProcessor {

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

    override suspend fun generateBlockResultModels(game: Game): AppResult<BlockItemData> =
        safeCall {
            val blockItems = mutableListOf<BlockItem>()
            blockItems.add(BlockPlaceholder())
            val players = game.gameInfo.players.names
            blockItems.addAll(players.map { BlockName(it) })
            game.rounds.forEach { round ->
                blockItems.add(BlockRound(round.card))
                blockItems.addAll(round.playerTippData.mapIndexed { index: Int, playerTippData: PlayerTippData ->
                    val resultData = round.playerResultData.getOrNull(index)
                    BlockResult(
                        player = players[index],
                        round = round.card,
                        tipp = playerTippData.tipp,
                        result = resultData?.result,
                        difference = resultData?.difference,
                        total = resultData?.total
                    )
                })
            }
            BlockItemData(
                items = blockItems,
                inputType = game.inputType,
                columnCount = game.gameInfo.players.names.size + 1
            )

        }

    override suspend fun getGameScores(game: Game): AppResult<GameScoreData> =
        safeCall {
            val gameFinished = game.currentRound == game.maxRound
            val players = game.gameInfo.players.names

            val lastRound = game.rounds.lastOrNull { it.playerResultData.isNotEmpty() }
            GameScoreData(
                finished = gameFinished,
                results = players.mapIndexed { index, name ->
                    GameScore(name, lastRound?.playerResultData?.get(index)?.total ?: 0)
                })
        }
}