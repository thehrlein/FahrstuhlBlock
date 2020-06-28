package com.tobiapplications.fahrstuhlblock.entities.models.game

import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import java.io.Serializable

data class Game(
    val players: PlayerSettingsData,
    val highCardCount: Int,
    val pointsRuleData: PointsRuleData
) : Serializable