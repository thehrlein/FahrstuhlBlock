package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.savedgames

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class GetAllSavedGamesUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, List<Game>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<Game>> {
        return gameRepository.getAllSavedGames()
    }
}
