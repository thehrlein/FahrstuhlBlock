package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.player

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class StorePlayerNamesUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<List<String>, Unit>() {

    override suspend fun execute(parameters: List<String>): AppResult<Unit> {
        return gameRepository.addPlayers(parameters)
    }
}
