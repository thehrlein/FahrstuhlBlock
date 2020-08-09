package com.tobiapplications.fahrstuhlblock.entities.models.game.input

import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData

data class CalculateResultData(
    val pointsRuleData: PointsRuleData,
    val resultData: List<ResultData>
)