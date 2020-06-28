package com.tobiapplications.fahrstuhlblock.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.Game
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class StoreGameUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Game, Long>() {

    override suspend fun execute(parameters: Game): AppResult<Long> {
        return gameRepository.storeGame(parameters)
    }
}