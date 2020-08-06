package com.tobiapplications.fahrstuhlblock.entities.models.game.general

import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import java.io.Serializable

private const val FIRST_ROUND = 1

data class Game(
    val gameInfo: GameInfo,
    val rounds: List<Round>
) : Serializable {

    val maxRound: Int
        get() = gameInfo.highCardCount * 2

    val previousTotals: List<Int>
        get() = when (rounds.size) {
            0, 1 -> gameInfo.players.names.map { 0 }
            else -> rounds[rounds.size - 2].playerResultData.map { it.total }
        }

    /**
     * Last round which was played, can be the current if not completed
     * Can be null if no round played yet
     */
    val lastPlayedRound: Round?
        get() = rounds.lastOrNull()

    val lastRoundNumber: Int?
        get() = lastPlayedRound?.round

    val inputType: InputType
        get() = lastPlayedRound?.let {
            if (it.roundCompleted) {
                InputType.TIPP
            } else {
                it.inputTypeForThisRound
            }
        } ?: InputType.TIPP

    val currentRoundNumber: Int
        get() = currentRound?.round ?: FIRST_ROUND

    /**
     * Returns current round or null if game is finished
     * Creates the next round if needed
     */
    val currentRound: Round?
        get() = when {
            lastRoundNumber == maxRound && lastPlayedRound?.roundCompleted == true -> null
            lastPlayedRound?.roundCompleted?.not() == true -> lastPlayedRound
            else -> createNewRound(rounds.size + 1, currentCardCount)
        }

    private fun createNewRound(round: Int, card: Int) = Round(round, card, emptyList(), emptyList(), TrumpType.NONE)

    val currentCardCount: Int
        get() = when {
            rounds.size < gameInfo.highCardCount -> {
                when {
                    rounds.isEmpty() -> 1
                    rounds.last().playerResultData.isEmpty() -> rounds.size
                    else -> rounds.size + 1
                }
            }
            rounds.size == gameInfo.highCardCount -> gameInfo.highCardCount
            else -> {
                val fullPlayedRounds = rounds.count { it.roundCompleted }
                gameInfo.highCardCount - (fullPlayedRounds - gameInfo.highCardCount)
            }
        }

    val gameFinished: Boolean
        get() = currentRound == null
}