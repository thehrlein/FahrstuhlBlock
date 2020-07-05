package com.tobiapplications.fahrstuhlblock.interactor.usecase.block

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.interactor.repository.GameRepository
import com.tobiapplications.fahrstuhlblock.interactor.usecase.BaseUseCase

data class InputsValidUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<CheckInputValidityData, Boolean>() {

    override suspend fun execute(parameters: CheckInputValidityData): AppResult<Boolean> {
        return gameRepository.checkInputsValidity(parameters)
    }
}