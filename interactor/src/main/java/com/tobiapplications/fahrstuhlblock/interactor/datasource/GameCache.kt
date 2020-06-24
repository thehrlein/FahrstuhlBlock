package com.tobiapplications.fahrstuhlblock.interactor.datasource

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.FahrstuhlGame

interface GameCache {

    suspend fun storeGame(fahrstuhlGame: FahrstuhlGame) : AppResult<Unit>

    suspend fun getGame() : AppResult<FahrstuhlGame>
}