package com.tobiapplications.fahrstuhlblock.fw_repositories.repository

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputDataItem
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockItemData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.GameScoreData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.MaxCardCountSelection
import com.tobiapplications.fahrstuhlblock.entities.models.settings.SettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.SettingsScreen
import com.tobiapplications.fahrstuhlblock.interactor.datasource.cache.GameCache
import com.tobiapplications.fahrstuhlblock.interactor.datasource.cache.PlayerCache
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockInputsProcessor
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockResultsProcessor
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository

class GameRepositoryImpl(
    private val gameCache: GameCache,
    private val playerCache: PlayerCache,
    private val blockInputsProcessor: BlockInputsProcessor,
    private val blockResultsProcessor: BlockResultsProcessor
) : GameRepository {

    override suspend fun storeGameInfo(gameInfo: GameInfo): AppResult<Long> {
        return gameCache.insertGameInfo(gameInfo)
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

    override suspend fun insertRound(roundData: InsertRoundData): AppResult<Unit> {
        return gameCache.insertRound(roundData)
    }

    override suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>> {
        return blockResultsProcessor.calculateResults(calculateResultData)
    }

    override suspend fun getBlockResults(game: Game): AppResult<BlockItemData> {
        return when (val result = blockResultsProcessor.generateBlockResultModels(game)) {
            is AppResult.Success -> result
            is AppResult.Error -> result
        }
    }

    override suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean> {
        return blockInputsProcessor.checkInputsValidity(inputValidityData)
    }

    override suspend fun getGameScores(game: Game): AppResult<GameScoreData> {
        return blockResultsProcessor.getGameScores(game)
    }

    override suspend fun getAllSavedGames(): AppResult<List<Game>> {
        return gameCache.getAllSavedGames()
    }

    override suspend fun setGameFinished(gameId: Long): AppResult<Unit> {
        return when (val result = gameCache.getGameInfo(gameId)) {
            is AppResult.Success -> {
                gameCache.insertGameInfo(
                    result.value.copy(
                        gameFinished = true
                    )
                ).map { Unit }
            }
            is AppResult.Error -> result
        }
    }

    override suspend fun getLastSettingsData(settingsScreen: SettingsScreen): AppResult<SettingsData> {
        return gameCache.getLastGameInfo().map { gameInfo ->
            when (settingsScreen) {
                SettingsScreen.PLAYER -> SettingsData.Player(gameInfo.players.names)
                SettingsScreen.CARDS -> when (gameInfo.maxCardCountSelection) {
                    MaxCardCountSelection.ONE_DECK -> SettingsData.Cards.OneDeck(gameInfo.stopElevatorAtHighCard, gameInfo.firstRoundTipsCanBeOne)
                    MaxCardCountSelection.TWO_DECKS -> SettingsData.Cards.TwoDecks(gameInfo.stopElevatorAtHighCard, gameInfo.firstRoundTipsCanBeOne)
                    else -> SettingsData.Cards.Individual(gameInfo.highCardCount, gameInfo.stopElevatorAtHighCard, gameInfo.firstRoundTipsCanBeOne)
                }
                SettingsScreen.POINTS -> SettingsData.Points(gameInfo.pointsRuleData)
            }
        }
    }

    override suspend fun getBlockInputData(game: Game): AppResult<InputData> {
        return blockInputsProcessor.getBlockInputModels(game)
    }

    override suspend fun removeRound(deleteRoundData: DeleteRoundData): AppResult<Unit> {
        return gameCache.removeRound(deleteRoundData)
    }
}