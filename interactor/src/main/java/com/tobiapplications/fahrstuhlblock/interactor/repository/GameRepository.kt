package com.tobiapplications.fahrstuhlblock.interactor.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.FahrstuhlGame

interface GameRepository {

    suspend fun storeGame(fahrstuhlGame: FahrstuhlGame): AppResult<Unit>

    suspend fun getGame() : AppResult<FahrstuhlGame>

    suspend fun addPlayers(names: List<String>): AppResult<Unit>

    suspend fun getAllPlayerNames() : AppResult<List<String>>
}