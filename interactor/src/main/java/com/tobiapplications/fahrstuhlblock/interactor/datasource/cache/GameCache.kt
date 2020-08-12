package com.tobiapplications.fahrstuhlblock.interactor.datasource.cache

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.entities.models.settings.SettingsData

interface GameCache {

    suspend fun insertGameInfo(gameInfo: GameInfo) : AppResult<Long>

    suspend fun getGameInfo(gameId: Long) : AppResult<GameInfo>

    suspend fun getGame(gameId: Long): AppResult<Game>

    suspend fun insertRound(roundData: InsertRoundData): AppResult<Unit>

    suspend fun removeRound(round: DeleteRoundData) : AppResult<Unit>

    suspend fun getAllSavedGames(): AppResult<List<Game>>

    suspend fun getLastGameInfo(): AppResult<GameInfo>
}