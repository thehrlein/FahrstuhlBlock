package com.tobiapplications.fahrstuhlblock.entities.models.settings

import java.io.Serializable

data class PointsRuleData(
    val correctPoints: Int,
    val pointsPerStitches: Int
) : Serializable