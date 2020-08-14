package com.tobiapplications.fahrstuhlblock.entities.models.game.general

import com.tobiapplications.fahrstuhlblock.entities.models.settings.MaxCardCountSelection
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData

data class GameInfo(
    val gameId: Long,
    val gameStartDate: Long,
    val players: PlayerSettingsData,
    val highCardCount: Int,
    val totalRounds: Int,
    val stopElevatorAtHighCard: Boolean,
    val maxCardCountSelection: MaxCardCountSelection,
    val pointsRuleData: PointsRuleData,
    val gameFinished: Boolean = false
)