package com.tobiapplications.fahrstuhlblock.entities.models.game.input

import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Round

data class InputData(
    val inputModels: List<InputDataItem>,
    val inputType: InputType,
    val currentRound: Round
)