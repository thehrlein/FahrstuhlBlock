package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.player

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class GetPlayerNamesUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, Set<String>>() {

    override suspend fun execute(parameters: Unit): AppResult<Set<String>> {
        return gameRepository.getAllPlayerNames()
    }
}
