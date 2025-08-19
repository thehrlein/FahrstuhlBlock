package com.tobiapplications.fahrstuhlblock.core.databaseroom.cache

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.core.databaseroom.dao.GameDao
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.mapper.mapToData
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.mapper.mapToDbData
import com.tobiapplications.fahrstuhlblock.core.interactor.SafeCaller
import com.tobiapplications.fahrstuhlblock.core.interactor.datasource.cache.GameCache
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

    override suspend fun removeRound(round: DeleteRoundData): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.removeRound(
                    gameId = round.gameId,
                    round = round.gameRound.round
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

    override suspend fun deleteGame(gameId: Long): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.deleteGame(gameId)
            }
        }
}
