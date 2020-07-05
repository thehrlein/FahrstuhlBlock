package com.tobiapplications.fahrstuhlblock.interactor.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData

interface BlockInputsProcessor {

    suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean>

}