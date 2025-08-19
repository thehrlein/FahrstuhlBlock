package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.general.GameInfo
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

class StoreGameInfoUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<GameInfo, Long>() {

    override suspend fun execute(parameters: GameInfo): AppResult<Long> {
        return gameRepository.storeGameInfo(parameters)
    }
}
