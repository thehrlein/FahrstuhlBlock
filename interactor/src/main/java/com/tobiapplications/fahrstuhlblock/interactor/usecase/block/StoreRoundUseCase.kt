package com.tobiapplications.fahrstuhlblock.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.InsertRoundData
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class StoreRoundUseCase(
    private val gameRepository: GameRepository
): BaseUseCase<InsertRoundData, Boolean>() {

    override suspend fun execute(parameters: InsertRoundData): AppResult<Boolean> {
        return gameRepository.insertRound(parameters)
    }
}