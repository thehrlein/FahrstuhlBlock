package com.tobiapplications.fahrstuhlblock.fw_repositories.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.interactor.SafeCaller
import com.tobiapplications.fahrstuhlblock.interactor.processor.BlockInputsProcessor

private const val FIRST_ROUND = 1

class BlockInputsProcessorImpl : SafeCaller, BlockInputsProcessor {

    override suspend fun checkInputsValidity(inputValidityData: CheckInputValidityData): AppResult<Boolean> =
        safeCall {
            val game = inputValidityData.game
            when (game.inputType) {
                InputType.TIPP -> {
                    if (game.currentRoundNumber == FIRST_ROUND) {
                        true
                    } else {
                        inputValidityData.inputSum != game.currentCardCount
                    }
                }
                InputType.RESULT -> inputValidityData.inputSum == game.currentCardCount
            }
        }
}