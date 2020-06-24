package com.tobiapplications.fahrstuhlblock.interactor.usecase.player

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class GetPlayerNamesUseCase(
    private val gameRepository: GameRepository
): BaseUseCase<Unit, List<String>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<String>> {
        return gameRepository.getAllPlayerNames()
    }
}