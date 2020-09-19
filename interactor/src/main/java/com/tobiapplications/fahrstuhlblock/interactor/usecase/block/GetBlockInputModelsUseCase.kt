package com.tobiapplications.fahrstuhlblock.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputData
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class GetBlockInputModelsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Game, InputData>() {

    override suspend fun execute(parameters: Game): AppResult<InputData> {
        return gameRepository.getBlockInputData(parameters)
    }
}
