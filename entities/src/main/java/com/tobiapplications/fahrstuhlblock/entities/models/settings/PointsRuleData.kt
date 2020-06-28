package com.tobiapplications.fahrstuhlblock.entities.models.settings

import java.io.Serializable

data class PointsRuleData(
    val correctPoints: Int,
    val pointsPerStitch: Int,
    val minusPointsPerStitch: Int,
    val pointsIfPredictionFalse: Boolean
) : Serializable