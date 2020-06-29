package com.tobiapplications.fahrstuhlblock.entities.models.game.input

data class InputData(
    val type: InputType,
    val player: String,
    val currentRound: Int,
    val cards: Int,
    var userInput: Int = 0
)