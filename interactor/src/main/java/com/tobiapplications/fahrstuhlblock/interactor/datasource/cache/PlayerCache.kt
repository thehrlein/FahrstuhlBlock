package com.tobiapplications.fahrstuhlblock.interactor.datasource.cache

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult

interface PlayerCache {

    suspend fun addPlayers(names: List<String>): AppResult<Unit>

    suspend fun getAllPlayerNames(): AppResult<Set<String>>
}
