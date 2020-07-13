package com.tobiapplications.fahrstuhlblock.fw_database_room.cache

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.GameInfo
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.InsertRoundData
import com.tobiapplications.fahrstuhlblock.fw_database_room.dao.GameDao
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper.mapToData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper.mapToDbData
import com.tobiapplications.fahrstuhlblock.interactor.SafeCaller
import com.tobiapplications.fahrstuhlblock.interactor.datasource.cache.GameCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameCacheImpl(
    private val gameDao: GameDao
) : SafeCaller,
    GameCache {

    override suspend fun storeGameInfo(gameInfo: GameInfo): AppResult<Long> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.insertGameInfo(gameInfo.mapToDbData())
            }
        }

    override suspend fun getGame(gameId: Long): AppResult<Game> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getGame(gameId).mapToData()
            }
        }

    override suspend fun insertRound(roundData: InsertRoundData): AppResult<Boolean> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.insertRound(roundData.round.mapToDbData(roundData.gameId))
                true
            }
        }
}