package com.tobiapplications.fahrstuhlblock.core.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.core.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.core.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.core.interactor.usecase.BaseUseCase

data class InputsValidUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<CheckInputValidityData, Boolean>() {

    override suspend fun execute(parameters: CheckInputValidityData): AppResult<Boolean> {
        return gameRepository.checkInputsValidity(parameters)
    }
}
