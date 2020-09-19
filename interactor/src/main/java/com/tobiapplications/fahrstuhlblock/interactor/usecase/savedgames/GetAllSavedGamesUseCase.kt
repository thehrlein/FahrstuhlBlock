package com.tobiapplications.fahrstuhlblock.interactor.usecase.savedgames

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class GetAllSavedGamesUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, List<Game>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<Game>> {
        return gameRepository.getAllSavedGames()
    }
}
