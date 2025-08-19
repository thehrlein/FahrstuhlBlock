package com.tobiapplications.fahrstuhlblock.core.entities.models.game.input

import com.tobiapplications.fahrstuhlblock.core.entities.models.game.general.Game

data class CheckInputValidityData(
    val game: Game,
    val inputSum: Int
)
