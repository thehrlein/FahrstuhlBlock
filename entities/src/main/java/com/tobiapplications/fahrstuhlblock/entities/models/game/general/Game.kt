package com.tobiapplications.fahrstuhlblock.entities.models.game.general

import com.tobiapplications.fahrstuhlblock.entities.models.game.input.Input
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import java.io.Serializable

private const val FIRST_ROUND = 1

data class Game(
    val gameInfo: GameInfo,
    val gameRounds: List<GameRound>
) : Serializable {

    val maxRound: Int
        get() = gameInfo.highCardCount * 2

    val previousTotals: List<Input>
        get() = when (gameRounds.size) {
            0, 1 -> gameInfo.players.names.map {
                Input(
                    playerName = it,
                    input = 0
                )
            }
            else -> gameRounds[gameRounds.size - 2].playerResultData.map {
                Input(
                    playerName = it.playerName,
                    input = it.total)
            }
        }

    /**
     * Last round which was played, can be the current if not completed
     * Can be null if no round played yet
     */
    val lastPlayedGameRound: GameRound?
        get() = gameRounds.lastOrNull()

    val lastRoundNumber: Int?
        get() = lastPlayedGameRound?.round

    val inputType: InputType
        get() = lastPlayedGameRound?.let {
            if (it.roundCompleted) {
                InputType.TIPP
            } else {
                it.inputTypeForThisRound
            }
        } ?: InputType.TIPP

    val currentRoundNumber: Int
        get() = currentGameRound?.round ?: FIRST_ROUND

    /**
     * Returns current round or null if game is finished
     * Creates the next round if needed
     */
    val currentGameRound: GameRound?
        get() = when {
            lastRoundNumber == maxRound && lastPlayedGameRound?.roundCompleted == true -> null
            lastPlayedGameRound?.roundCompleted?.not() == true -> lastPlayedGameRound
            else -> createNewRound(gameRounds.size + 1, currentCardCount)
        }

    private fun createNewRound(round: Int, card: Int) = GameRound(round, card, emptyList(), emptyList(), TrumpType.NONE)

    val currentCardCount: Int
        get() = when {
            gameRounds.size < gameInfo.highCardCount -> {
                when {
                    gameRounds.isEmpty() -> 1
                    gameRounds.last().playerResultData.isEmpty() -> gameRounds.size
                    else -> gameRounds.size + 1
                }
            }
            gameRounds.size == gameInfo.highCardCount -> gameInfo.highCardCount
            else -> {
                val fullPlayedRounds = gameRounds.count { it.roundCompleted }
                gameInfo.highCardCount - (fullPlayedRounds - gameInfo.highCardCount)
            }
        }

    val gameFinished: Boolean
        get() = currentGameRound == null
}