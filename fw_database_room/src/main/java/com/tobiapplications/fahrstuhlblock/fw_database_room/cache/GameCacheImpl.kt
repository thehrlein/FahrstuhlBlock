package com.tobiapplications.fahrstuhlblock.fw_database_room.cache

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.fw_database_room.dao.GameDao
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper.mapToData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper.mapToDbData
import com.tobiapplications.fahrstuhlblock.interactor.SafeCaller
import com.tobiapplications.fahrstuhlblock.interactor.datasource.cache.GameCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameCacheImpl(
    private val gameDao: GameDao
) : SafeCaller, GameCache {

    override suspend fun insertGameInfo(gameInfo: GameInfo): AppResult<Long> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.insertGameInfo(gameInfo.mapToDbData())
            }
        }

    override suspend fun getGameInfo(gameId: Long): AppResult<GameInfo> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getGameInfo(gameId).mapToData()
            }
        }

    override suspend fun getGame(gameId: Long): AppResult<Game> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getGame(gameId).mapToData()
            }
        }

    override suspend fun insertRound(roundData: InsertRoundData): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.insertRound(roundData.gameRound.mapToDbData(roundData.gameId))
                Unit
            }
        }

    override suspend fun removeRound(deleteRoundData: DeleteRoundData): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.removeRound(
                    gameId = deleteRoundData.gameId,
                    round = deleteRoundData.gameRound.round
                )
            }
        }

    override suspend fun getAllSavedGames(): AppResult<List<Game>> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getAllSavedGames().map {
                    it.mapToData()
                }
            }
        }

    override suspend fun getLastGameInfo(): AppResult<GameInfo> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getLastGameInfo().mapToData()
            }
        }
}