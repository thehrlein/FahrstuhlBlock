package com.tobiapplications.fahrstuhlblock.interactor.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.InsertRoundData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData

interface GameRepository {

    suspend fun storeGame(game: Game): AppResult<Long>

    suspend fun getGame(gameId: Long): AppResult<Game>

    suspend fun addPlayers(names: List<String>): AppResult<Unit>

    suspend fun getAllPlayerNames(): AppResult<List<String>>

    suspend fun insertRound(roundData: InsertRoundData): AppResult<Boolean>

    suspend fun calculateResults(parameters: CalculateResultData): AppResult<List<PlayerResultData>>
}