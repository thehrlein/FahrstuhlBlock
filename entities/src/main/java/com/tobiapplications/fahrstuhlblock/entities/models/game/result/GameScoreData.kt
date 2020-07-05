package com.tobiapplications.fahrstuhlblock.entities.models.game.result

import java.io.Serializable

data class GameScoreData(
    val finished: Boolean,
    val results: List<GameScore>
) : Serializable