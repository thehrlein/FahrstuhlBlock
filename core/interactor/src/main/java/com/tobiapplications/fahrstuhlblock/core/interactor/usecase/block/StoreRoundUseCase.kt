package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.general.InsertRoundData
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class StoreRoundUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<InsertRoundData, Unit>() {

    override suspend fun execute(parameters: InsertRoundData): AppResult<Unit> {
        return gameRepository.insertRound(parameters)
    }
}
