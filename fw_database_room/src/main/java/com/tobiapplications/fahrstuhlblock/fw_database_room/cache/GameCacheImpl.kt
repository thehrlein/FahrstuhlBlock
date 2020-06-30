package com.tobiapplications.fahrstuhlblock.fw_database_room.cache

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.InsertRoundData
import com.tobiapplications.fahrstuhlblock.fw_database_room.dao.GameDao
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper.mapToData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper.mapToDbData
import com.tobiapplications.fahrstuhlblock.interactor.datasource.GameCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameCacheImpl(
    private val gameDao: GameDao
) : BaseCache, GameCache {

    override suspend fun storeGame(game: Game): AppResult<Long> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.insertGame(game.mapToDbData())
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
                val game = gameDao.getGame(roundData.gameId)
                val rounds = game.rounds.toMutableList()
                if (rounds.lastOrNull()?.card == roundData.round.card) {
                    rounds.remove(rounds.last())
                }
                rounds.add(roundData.round.mapToDbData())
                gameDao.insertGame(game.copy(
                    rounds = rounds
                ))

                true
            }
        }
}