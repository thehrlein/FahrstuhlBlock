package com.tobiapplications.fahrstuhlblock.interactor.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData

interface ResultsCalculatorProcessor {

    suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>>
}