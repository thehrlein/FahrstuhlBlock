package com.tobiapplications.fahrstuhlblock.core.entities.models.settings

import java.io.Serializable

data class PointsRuleData(
    val correctPoints: Int,
    val pointsPerStitch: Int,
    val minusPointsPerStitch: Int,
    val pointsIfPredictionFalse: Boolean
) : Serializable
