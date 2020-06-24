package com.tobiapplications.fahrstuhlblock.entities.models.game

import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import java.io.Serializable

data class FahrstuhlGame(
    val gameRuleSettingsData: GameRuleSettingsData,
    val pointsRuleData: PointsRuleData
) : Serializable