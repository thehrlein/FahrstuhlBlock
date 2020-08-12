package com.tobiapplications.fahrstuhlblock.interactor.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputDataItem
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockItemData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.GameScoreData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.SettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.SettingsScreen

interface GameRepository {

    suspend fun storeGameInfo(gameInfo: GameInfo): AppResult<Long>

    suspend fun getGame(gameId: Long): AppResult<Game>

    suspend fun addPlayers(names: List<String>): AppResult<Unit>

    suspend fun getAllPlayerNames(): AppResult<Set<String>>

    suspend fun insertRound(roundData: InsertRoundData): AppResult<Unit>

    suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>>

    suspend fun getBlockResults(game: Game): AppResult<BlockItemData>

    suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean>

    suspend fun getGameScores(game: Game): AppResult<GameScoreData>

    suspend fun getAllSavedGames(): AppResult<List<Game>>

    suspend fun setGameFinished(gameId: Long) : AppResult<Unit>

    suspend fun getLastSettingsData(settingsScreen: SettingsScreen): AppResult<SettingsData>

    suspend fun getBlockInputData(game: Game): AppResult<InputData>

    suspend fun removeRound(deleteRoundData: DeleteRoundData): AppResult<Unit>
}