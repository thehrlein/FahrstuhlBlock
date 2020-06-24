package com.tobiapplications.fahrstuhlblock.fw_database_room.cache

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.FahrstuhlGame
import com.tobiapplications.fahrstuhlblock.fw_database_room.dao.GameDao
import com.tobiapplications.fahrstuhlblock.fw_database_room.dao.PlayerDao
import com.tobiapplications.fahrstuhlblock.interactor.datasource.GameCache
import com.tobiapplications.fahrstuhlblock.interactor.datasource.PlayerCache

class GameCacheImpl(
    private val gameDao: GameDao
) : BaseCache, GameCache {

    override suspend fun storeGame(fahrstuhlGame: FahrstuhlGame) : AppResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getGame(): AppResult<FahrstuhlGame> {
        TODO("Not yet implemented")
    }
}