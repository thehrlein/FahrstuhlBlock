package com.tobiapplications.fahrstuhlblock.fw_repositories.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerTippData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.*
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockResultsProcessor
import com.tobiapplications.fahrstuhlblock.ui_common.extension.isOdd
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
            val currentRound = game.currentRound
            blockItems.addAll(players.mapIndexed { index: Int, name: String ->
                BlockName(
                    name = name,
                    isDealer = isDealer(currentRound, game.maxRound, players.size, index + 1)
                )
            })
            game.rounds.forEach { round ->
                blockItems.add(
                    BlockRound(
                        round = round.card,
                        colorized = round.card.isOdd()
                    )
                )
                blockItems.addAll(round.playerTippData.mapIndexed { index: Int, playerTippData: PlayerTippData ->
                    val resultData = round.playerResultData.getOrNull(index)
                    BlockResult(
                        player = players[index],
                        round = round.card,
                        tipp = playerTippData.tipp,
                        result = resultData?.result,
                        difference = resultData?.difference,
                        total = resultData?.total,
                        colorized = round.card.isOdd()
                    )
                })
            }

            val columnCount = game.gameInfo.players.names.size * 2 + 1
            BlockItemData(
                items = blockItems,
                inputType = game.inputType,
                columnCount = columnCount
            )

        }

    private fun isDealer(
        round: Int,
        maxRound: Int,
        playerCount: Int,
        playerPosition: Int
    ): Boolean {
        val remainder = round % playerCount
        return when {
            round > maxRound -> false
            playerPosition == playerCount -> remainder == 0
            else -> remainder == playerPosition
        }
    }

    override suspend fun getGameScores(game: Game): AppResult<GameScoreData> =
        safeCall {
            val gameFinished = game.currentRound > game.maxRound
            val players = game.gameInfo.players.names

            val lastRound = game.rounds.lastOrNull { it.playerResultData.isNotEmpty() }
            val scores = mutableListOf<GameScore>()
            players.mapIndexed { index, name ->
                Pair(name, lastRound?.playerResultData?.get(index)?.total ?: 0)
            }.groupBy { it.second }
                .entries
                .sortedByDescending { it.key }
                .mapIndexed { index: Int, entry: Map.Entry<Int, List<Pair<String, Int>>> ->
                    scores.addAll(entry.value.map {
                        GameScore(index + 1, it.first, it.second)
                    })
                }

            GameScoreData(
                finished = gameFinished,
                results = scores
            )
        }
}