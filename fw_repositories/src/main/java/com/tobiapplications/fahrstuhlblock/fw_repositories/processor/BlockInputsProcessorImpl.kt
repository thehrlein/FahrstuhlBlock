package com.tobiapplications.fahrstuhlblock.fw_repositories.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Round
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputDataItem
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.interactor.SafeCaller
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockInputsProcessor
import com.tobiapplications.fahrstuhlblock.ui_common.utils.BlockHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val FIRST_ROUND = 1
private const val DEFAULT_PLAYER_INPUT = 0

class BlockInputsProcessorImpl : SafeCaller, BlockInputsProcessor {

    override suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean> =
        withContext(Dispatchers.IO) {
            safeCall {
                val game = inputValidityData.game
                when (game.inputType) {
                    InputType.TIPP -> {
                        if (game.currentRoundNumber == FIRST_ROUND) {
                            true
                        } else {
                            inputValidityData.inputSum != game.currentCardCount
                        }
                    }
                    InputType.RESULT -> inputValidityData.inputSum == game.currentCardCount
                }
            }
        }

    @ExperimentalStdlibApi
    override suspend fun getBlockInputModels(game: Game): AppResult<InputData> =
        withContext(Dispatchers.IO) {
            safeCall {
                val round = game.currentRound ?: error("current round could not be determined")
                val inputType = round.inputTypeForThisRound ?: InputType.TIPP
                InputData(
                    inputModels = getCorrectOrderInputList(game, inputType, round),
                    inputType = inputType,
                    currentRound = round
                )
            }
        }

    @ExperimentalStdlibApi
    private fun getCorrectOrderInputList(
        game: Game,
        inputType: InputType,
        round: Round
    ): List<InputDataItem> {
        val inputModels = game.gameInfo.players.names.mapIndexed { index: Int, name: String ->
            InputDataItem(
                type = inputType,
                player = name,
                isDealer = BlockHelper.isDealer(
                    round.round,
                    game.maxRound,
                    game.gameInfo.players.names.size,
                    index + 1
                ),
                currentRound = game.currentRoundNumber,
                cards = game.currentCardCount,
                userInput = round.playerTippData.firstOrNull { it.playerName == name }?.tipp
                    ?: DEFAULT_PLAYER_INPUT
            )
        }

        val newList = ArrayList(inputModels)
        while (newList.last().isDealer.not()) {
            val lastOne = newList.removeLast()
            newList.add(0, lastOne)
        }

        return newList
    }
}