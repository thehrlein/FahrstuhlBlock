package com.tobiapplications.fahrstuhlblock.fw_repositories.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.GameInfo
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.InsertRoundData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockItemData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.GameScoreData
import com.tobiapplications.fahrstuhlblock.interactor.datasource.GameCache
import com.tobiapplications.fahrstuhlblock.interactor.datasource.PlayerCache
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockInputsProcessor
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockResultsProcessor
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository

class GameRepositoryImpl(
    private val gameCache: GameCache,
    private val playerCache: PlayerCache,
    private val blockInputsProcessor: BlockInputsProcessor,
    private val blockResultsProcessor: BlockResultsProcessor
) : GameRepository {

    override suspend fun storeGameInfo(gameInfo: GameInfo) : AppResult<Long> {
        return gameCache.storeGameInfo(gameInfo)
    }

    override suspend fun getGame(gameId: Long): AppResult<Game> {
        return gameCache.getGame(gameId)
    }

    override suspend fun addPlayers(names: List<String>): AppResult<Unit> {
        return playerCache.addPlayers(names)
    }

    override suspend fun getAllPlayerNames(): AppResult<Set<String>> {
        return playerCache.getAllPlayerNames()
    }

    override suspend fun insertRound(roundData: InsertRoundData): AppResult<Boolean> {
        return gameCache.insertRound(roundData)
    }

    override suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>> {
        return blockResultsProcessor.calculateResults(calculateResultData)
    }

    override suspend fun getBlockResults(gameId: Long): AppResult<BlockItemData> {
        return when (val result = getGame(gameId)) {
            is AppResult.Success -> blockResultsProcessor.generateBlockResultModels(result.value)
            is AppResult.Error -> result
        }
    }

    override suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean> {
        return blockInputsProcessor.checkInputsValidity(inputValidityData)
    }

    override suspend fun getGameScores(game: Game): AppResult<GameScoreData> {
        return blockResultsProcessor.getGameScores(game)
    }
}