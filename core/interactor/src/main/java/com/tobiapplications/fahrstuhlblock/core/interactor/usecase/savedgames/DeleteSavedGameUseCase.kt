package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.savedgames

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class DeleteSavedGameUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Long, Unit>() {

    override suspend fun execute(parameters: Long): AppResult<Unit> {
        return gameRepository.deleteGame(parameters)
    }
}
