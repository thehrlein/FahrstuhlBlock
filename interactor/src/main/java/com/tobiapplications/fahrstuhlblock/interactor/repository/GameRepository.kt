package com.tobiapplications.fahrstuhlblock.interactor.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.GameInfo
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.InsertRoundData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockItemData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.GameScoreData

interface GameRepository {

    suspend fun storeGameInfo(gameInfo: GameInfo): AppResult<Long>

    suspend fun getGame(gameId: Long): AppResult<Game>

    suspend fun addPlayers(names: List<String>): AppResult<Unit>

    suspend fun getAllPlayerNames(): AppResult<Set<String>>

    suspend fun insertRound(roundData: InsertRoundData): AppResult<Boolean>

    suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>>

    suspend fun getBlockResults(game: Game): AppResult<BlockItemData>

    suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean>

    suspend fun getGameScores(game: Game): AppResult<GameScoreData>

    suspend fun getAllSavedGames(): AppResult<List<Game>>

    suspend fun setGameFinished(gameId: Long) : AppResult<Unit>
}