package com.tobiapplications.fahrstuhlblock.core.entities.models.game.input

import com.tobiapplications.fahrstuhlblock.core.entities.models.settings.PointsRuleData

data class CalculateResultData(
    val pointsRuleData: PointsRuleData,
    val resultData: List<ResultData>
)
