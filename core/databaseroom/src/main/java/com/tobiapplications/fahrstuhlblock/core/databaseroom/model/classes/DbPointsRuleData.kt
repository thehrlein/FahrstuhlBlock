package com.tobiapplications.fahrstuhlblock.core.databaseroom.model.classes

data class DbPointsRuleData(
    val correctPoints: Int,
    val pointsPerStitch: Int,
    val minusPointsPerStitch: Int,
    val pointsIfPredictionFalse: Boolean
)
