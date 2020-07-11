package com.tobiapplications.fahrstuhlblock.fw_database_room.cache

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.fw_database_room.dao.PlayerDao
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity.DbPlayer
import com.tobiapplications.fahrstuhlblock.interactor.datasource.cache.PlayerCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerCacheImpl(
    private val playerDao: PlayerDao
) : BaseCache,
    PlayerCache {

    override suspend fun addPlayers(names: List<String>): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                playerDao.insertPlayerNames(names.map {
                    DbPlayer(
                        it
                    )
                })
            }
        }

    override suspend fun getAllPlayerNames(): AppResult<Set<String>> =
        withContext(Dispatchers.IO) {
            safeCall {
                playerDao.queryAllPlayerNames().map {
                    it.name
                }.toSet()
            }
        }
}