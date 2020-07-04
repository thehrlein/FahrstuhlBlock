package com.tobiapplications.fahrstuhlblock.entities.models.game.general

import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType

data class Round(
    val card: Int,
    val playerTippData: List<PlayerTippData>,
    val playerResultData: List<PlayerResultData>
) {

    val currentInputType: InputType
        get() = if (playerTippData.isNullOrEmpty()) {
            InputType.TIPP
        } else {
            InputType.RESULT
        }

    val roundCompleted : Boolean
        get() = !playerResultData.isNullOrEmpty()
}