package com.tobiapplications.fahrstuhlblock.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.GameScoreData
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class GetGameScoresUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Game, GameScoreData>() {

    override suspend fun execute(parameters: Game): AppResult<GameScoreData> {
        return gameRepository.getGameScores(parameters)
    }
}