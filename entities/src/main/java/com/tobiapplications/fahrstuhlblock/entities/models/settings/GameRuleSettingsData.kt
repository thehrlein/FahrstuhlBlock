package com.tobiapplications.fahrstuhlblock.entities.models.settings

import java.io.Serializable

data class GameRuleSettingsData(
    val playerSettingsData: PlayerSettingsData,
    val highCardCount: Int,
    val totalRounds: Int,
    val stopElevatorAtHighCard: Boolean,
    val maxCardCountSelection: MaxCardCountSelection,
    val firstRoundTipsCanBeOne: Boolean
) : Serializable
