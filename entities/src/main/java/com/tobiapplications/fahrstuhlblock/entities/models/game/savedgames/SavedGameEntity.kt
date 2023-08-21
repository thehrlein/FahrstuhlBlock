package com.tobiapplications.fahrstuhlblock.entities.models.game.savedgames

data class SavedGameEntity(
    val gameId: Long,
    val gameStartDate: Long,
    val players: List<String>,
    val currentRound: Int,
    val maxRound: Int,
    val gameFinished: Boolean
)
