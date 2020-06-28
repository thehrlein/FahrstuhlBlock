package com.tobiapplications.fahrstuhlblock.interactor.datasource

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult

interface PlayerCache {

    suspend fun addPlayers(names: List<String>) : AppResult<Unit>

    suspend fun getAllPlayerNames() : AppResult<List<String>>
}