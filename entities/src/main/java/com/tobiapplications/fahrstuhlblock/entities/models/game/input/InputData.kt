package com.tobiapplications.fahrstuhlblock.entities.models.game.input

import com.tobiapplications.fahrstuhlblock.entities.models.game.general.GameRound

data class InputData(
    val inputModels: List<InputDataItem>,
    val inputType: InputType,
    val currentGameRound: GameRound
)