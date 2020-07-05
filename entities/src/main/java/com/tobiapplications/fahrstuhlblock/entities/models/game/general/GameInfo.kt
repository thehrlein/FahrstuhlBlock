package com.tobiapplications.fahrstuhlblock.entities.models.game.general

import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData

data class GameInfo(
    val players: PlayerSettingsData,
    val highCardCount: Int,
    val pointsRuleData: PointsRuleData
)