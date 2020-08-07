package com.tobiapplications.fahrstuhlblock.entities.models.game.input

data class InputDataItem(
    val type: InputType,
    val player: String,
    val isDealer: Boolean,
    val currentRound: Int,
    val cards: Int,
    var userInput: Int = 0
)