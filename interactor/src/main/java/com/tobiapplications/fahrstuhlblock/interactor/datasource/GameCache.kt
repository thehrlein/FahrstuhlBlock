package com.tobiapplications.fahrstuhlblock.interactor.datasource

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.Game

interface GameCache {

    suspend fun storeGame(game: Game) : AppResult<Long>

    suspend fun getGame(gameId: Long): AppResult<Game>
}