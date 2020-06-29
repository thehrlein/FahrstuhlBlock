package com.tobiapplications.fahrstuhlblock.entities.models.game.general

import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType

data class Round(
    val card: Int,
    val playerTippData: List<PlayerTippData>,
    val playerResultData: List<PlayerResultData>
) {

    fun currentInputType(): InputType {
        return if (playerResultData.isNullOrEmpty()) {
            InputType.RESULT
        } else {
            InputType.TIPP
        }
    }
}