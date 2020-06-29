package com.tobiapplications.fahrstuhlblock.entities.models.game.general

import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import java.io.Serializable

data class Game(
    val players: PlayerSettingsData,
    val highCardCount: Int,
    val pointsRuleData: PointsRuleData,
    val rounds: List<Round>
) : Serializable {

    fun currentCardCount(): Int {
        return if (rounds.size < highCardCount) {
            rounds.size + 1
        } else {
            highCardCount - rounds.size
        }
    }
}