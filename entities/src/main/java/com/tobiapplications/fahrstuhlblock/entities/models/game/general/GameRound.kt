package com.tobiapplications.fahrstuhlblock.entities.models.game.general

import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType

data class GameRound(
    val round: Int,
    val cardCount: Int,
    val playerTippData: List<PlayerTippData>,
    val playerResultData: List<PlayerResultData>,
    val trumpType: TrumpType
) {

    val inputTypeForThisRound: InputType?
        get() = when {
            roundCompleted -> null
            playerTippData.isEmpty() -> InputType.TIPP
            else -> InputType.RESULT
        }

    val roundCompleted: Boolean
        get() = !playerResultData.isNullOrEmpty()
}