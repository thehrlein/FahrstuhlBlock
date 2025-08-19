package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class GetGameUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Long, Game>() {

    override suspend fun execute(parameters: Long): AppResult<Game> {
        return gameRepository.getGame(parameters)
    }
}
