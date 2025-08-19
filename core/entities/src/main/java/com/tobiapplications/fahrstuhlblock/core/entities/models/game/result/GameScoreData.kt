package com.tobiapplications.fahrstuhlblock.core.entities.models.game.result

import java.io.Serializable

data class GameScoreData(
    val finished: Boolean,
    val winnerAlreadyShown: Boolean,
    val results: List<GameScore>
) : Serializable
