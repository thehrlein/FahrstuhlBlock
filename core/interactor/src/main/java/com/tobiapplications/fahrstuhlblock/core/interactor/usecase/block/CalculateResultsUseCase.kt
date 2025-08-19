package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class CalculateResultsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<CalculateResultData, List<PlayerResultData>>() {

    override suspend fun execute(parameters: CalculateResultData): AppResult<List<PlayerResultData>> {
        return gameRepository.calculateResults(parameters)
    }
}
