package com.tobiapplications.fahrstuhlblock.interactor.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputDataItem

interface BlockInputsProcessor {

    suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean>

    suspend fun getBlockInputModels(game: Game): AppResult<InputData>
}