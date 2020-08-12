package com.tobiapplications.fahrstuhlblock.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.DeleteRoundData
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class RemoveRoundUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<DeleteRoundData, Unit>() {

    override suspend fun execute(parameters: DeleteRoundData): AppResult<Unit> {
        return gameRepository.removeRound(parameters)
    }
}