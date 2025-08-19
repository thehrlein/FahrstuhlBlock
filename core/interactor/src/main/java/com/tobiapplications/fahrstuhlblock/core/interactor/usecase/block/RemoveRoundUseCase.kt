package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.general.DeleteRoundData
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class RemoveRoundUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<DeleteRoundData, Unit>() {

    override suspend fun execute(parameters: DeleteRoundData): AppResult<Unit> {
        return gameRepository.removeRound(parameters)
    }
}
