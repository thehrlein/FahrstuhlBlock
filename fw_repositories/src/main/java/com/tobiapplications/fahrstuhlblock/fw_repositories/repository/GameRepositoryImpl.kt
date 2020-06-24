package com.tobiapplications.fahrstuhlblock.fw_repositories.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.FahrstuhlGame
import com.tobiapplications.fahrstuhlblock.interactor.datasource.GameCache
import com.tobiapplications.fahrstuhlblock.interactor.datasource.PlayerCache
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository

class GameRepositoryImpl(
    private val gameCache: GameCache,
    private val playerCache: PlayerCache
) : GameRepository {

    override suspend fun storeGame(fahrstuhlGame: FahrstuhlGame) : AppResult<Unit> {
        return gameCache.storeGame(fahrstuhlGame)
    }

    override suspend fun getGame(): AppResult<FahrstuhlGame> {
        return gameCache.getGame()
    }

    override suspend fun addPlayers(names: List<String>): AppResult<Unit> {
        return playerCache.addPlayers(names)
    }

    override suspend fun getAllPlayerNames(): AppResult<List<String>> {
        return playerCache.getAllPlayerNames()
    }
}