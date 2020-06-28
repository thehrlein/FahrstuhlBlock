package com.tobiapplications.fahrstuhlblock.interactor.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.Game

interface GameRepository {

    suspend fun storeGame(game: Game): AppResult<Long>

    suspend fun getGame(gameId: Long): AppResult<Game>

    suspend fun addPlayers(names: List<String>): AppResult<Unit>

    suspend fun getAllPlayerNames() : AppResult<List<String>>
}