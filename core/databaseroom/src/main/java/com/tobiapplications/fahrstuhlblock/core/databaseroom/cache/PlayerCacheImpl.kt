package com.tobiapplications.fahrstuhlblock.core.databaseroom.cache

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.databaseroom.dao.PlayerDao
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.entity.DbPlayer
import com.tobiapplications.fahrstuhlblock.core.interactor.SafeCaller
import com.tobiapplications.fahrstuhlblock.core.interactor.datasource.cache.PlayerCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerCacheImpl(
    private val playerDao: PlayerDao
) : SafeCaller,
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
