package com.tobiapplications.fahrstuhlblock.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.GameInfo
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

class StoreGameInfoUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<GameInfo, Long>() {

    override suspend fun execute(parameters: GameInfo): AppResult<Long> {
        return gameRepository.storeGameInfo(parameters)
    }
}
