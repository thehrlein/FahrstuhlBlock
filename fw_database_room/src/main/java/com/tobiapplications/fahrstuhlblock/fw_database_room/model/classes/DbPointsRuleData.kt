package com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes

data class DbPointsRuleData(
    val correctPoints: Int,
    val pointsPerStitch: Int,
    val minusPointsPerStitch: Int,
    val pointsIfPredictionFalse: Boolean
)