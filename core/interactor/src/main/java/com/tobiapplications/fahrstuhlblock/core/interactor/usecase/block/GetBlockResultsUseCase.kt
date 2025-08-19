package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.result.BlockItemData
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class GetBlockResultsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Game, BlockItemData>() {

    override suspend fun execute(parameters: Game): AppResult<BlockItemData> {
        return gameRepository.getBlockResults(parameters)
    }
}
