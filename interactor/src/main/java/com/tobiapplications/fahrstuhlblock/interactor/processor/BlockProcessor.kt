package com.tobiapplications.fahrstuhlblock.interactor.processor

import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockItem
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockItemData

interface BlockProcessor {

    suspend fun calculateResults(calculateResultData: CalculateResultData): AppResult<List<PlayerResultData>>

    suspend fun generateBlockResultModels(game: Game): AppResult<BlockItemData>
}