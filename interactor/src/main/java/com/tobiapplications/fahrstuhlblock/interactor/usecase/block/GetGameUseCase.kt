package com.tobiapplications.fahrstuhlblock.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class GetGameUseCase(
    private val gameRepository: GameRepository
): BaseUseCase<Long, Game>() {

    override suspend fun execute(parameters: Long): AppResult<Game> {
        return gameRepository.getGame(parameters)
    }
}