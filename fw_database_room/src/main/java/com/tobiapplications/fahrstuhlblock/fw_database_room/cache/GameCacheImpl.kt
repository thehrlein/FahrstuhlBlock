package com.tobiapplications.fahrstuhlblock.fw_database_room.cache

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.Game
import com.tobiapplications.fahrstuhlblock.fw_database_room.dao.GameDao
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter.mapToData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter.mapToDbData
import com.tobiapplications.fahrstuhlblock.interactor.datasource.GameCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameCacheImpl(
    private val gameDao: GameDao
) : BaseCache, GameCache {

    override suspend fun storeGame(game: Game): AppResult<Long> =
        withContext(Dispatchers.IO) {
            safeCallDevice {
                gameDao.insertGame(game.mapToDbData())
            }
        }

    override suspend fun getGame(gameId: Long): AppResult<Game> =
        withContext(Dispatchers.IO) {
            safeCallDevice {
                gameDao.getGame(gameId).mapToData()
            }
        }
}