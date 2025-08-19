package com.tobiapplications.fahrstuhlblock.core.interactor.datasource.cache

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult

interface PlayerCache {

    suspend fun addPlayers(names: List<String>): AppResult<Unit>

    suspend fun getAllPlayerNames(): AppResult<Set<String>>
}
