package com.tobiapplications.fahrstuhlblock.fw_repositories.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerTippData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.*
import com.tobiapplications.fahrstuhlblock.interactor.SafeCaller
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockResultsProcessor
import com.tobiapplications.fahrstuhlblock.ui_common.extension.isOdd
import com.tobiapplications.fahrstuhlblock.ui_common.utils.BlockHelper
import kotlin.math.abs

class BlockResultsProcessorImpl : SafeCaller, BlockResultsProcessor {

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
        val playerResultData = mutableListOf<PlayerResultData>()
        data.resultData.forEach { resultData ->
            var points = 0
            val result = resultData.result
            points += result * pointRules.pointsPerStitch
            if (resultData.tipp == result) {
                points += pointRules.correctPoints
            }

            playerResultData.add(
                PlayerResultData(
                    playerName = resultData.playerName,
                    result = result,
                    difference = points,
                    total = resultData.previousTotal + points
                )
            )
        }

        return playerResultData
    }


    private fun calculateWithoutPointsIfPredictionIsFalse(data: CalculateResultData): List<PlayerResultData> {
        val pointRules = data.pointsRuleData
        val playerResultData = mutableListOf<PlayerResultData>()
        data.resultData.forEach { resultData ->
            var points = 0
            val result = resultData.result

            points += if (resultData.tipp == result) {
                result * pointRules.pointsPerStitch
            } else {
                -abs(resultData.tipp - result) * pointRules.minusPointsPerStitch
            }
            if (resultData.tipp == result) {
                points += pointRules.correctPoints
            }

            playerResultData.add(
                PlayerResultData(
                    playerName = resultData.playerName,
                    result = result,
                    difference = points,
                    total = resultData.previousTotal + points
                )
            )
        }

        return playerResultData
    }

    override suspend fun generateBlockResultModels(game: Game): AppResult<BlockItemData> =
        safeCall {
            val blockItems = mutableListOf<BlockItem>()
            blockItems.add(BlockPlaceholder(game.currentRound?.trumpType ?: TrumpType.NONE))
            val players = game.gameInfo.players.names
            val currentRoundNumber = game.currentRoundNumber
            blockItems.addAll(players.mapIndexed { index: Int, name: String ->
                BlockName(
                    name = name,
                    isDealer = BlockHelper.isDealer(currentRoundNumber, game.maxRound, players.size, index + 1)
                )
            })
            game.rounds.forEach { round ->
                if (round.playerTippData.isEmpty()) return@forEach // do not show round number after trump selected and without any tipps made
                blockItems.add(
                    BlockRound(
                        round = round.cardCount,
                        colorized = round.round.isOdd()
                    )
                )
                blockItems.addAll(players.map { player ->
                    val resultData = round.playerResultData.firstOrNull { it.playerName == player }
                    BlockResult(
                        player = player,
                        round = round.round,
                        tipp = round.playerTippData.first { it.playerName == player }.tipp,
                        result = resultData?.result,
                        difference = resultData?.difference,
                        total = resultData?.total,
                        colorized = round.round.isOdd()
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

    override suspend fun getGameScores(game: Game): AppResult<GameScoreData> =
        safeCall {
            val gameFinished = game.gameFinished
            val players = game.gameInfo.players.names

            val lastRound = game.rounds.lastOrNull { it.playerResultData.isNotEmpty() }
            val scores = mutableListOf<GameScore>()
            players.mapIndexed { index, name ->
                Pair(name, lastRound?.playerResultData?.firstOrNull { it.playerName == name }?.total ?: 0)
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
                winnerAlreadyShown = game.gameInfo.gameFinished,
                results = scores
            )
        }
}