package com.tobiapplications.fahrstuhlblock.entities.models.game.general

import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import java.io.Serializable

data class Game(
    val gameInfo: GameInfo,
    val rounds: List<Round>
) : Serializable {

    val currentCardCount: Int
        get() = if (rounds.size < gameInfo.highCardCount) {
            when {
                rounds.isEmpty() -> 1
                rounds.last().playerResultData.isEmpty() -> rounds.size
                else -> rounds.size + 1
            }
        } else {
            gameInfo.highCardCount - rounds.size
        }

    val currentRound: Int
        get() = rounds.size + 1

    val inputType: InputType
        get() = rounds.lastOrNull()?.currentInputType ?: InputType.TIPP

    val previousTotals: List<Int>
        get() = if (rounds.size == 1) {
            gameInfo.players.names.map { 0 }
        } else {
            rounds[rounds.size - 1].playerResultData.map { it.total }
        }
}