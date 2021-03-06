package com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbMaxCardCountSelection
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbPlayerSettingsData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbPointsRuleData

@Entity(tableName = "game_database")
data class DbGameInfo(
    @PrimaryKey(autoGenerate = true)
    val gameId: Long,
    val gameStartDate: Long,
    @Embedded
    val players: DbPlayerSettingsData,
    val highCardCount: Int,
    val totalRounds: Int,
    val stopElevatorAtHighCard: Boolean,
    val firstRoundTipsCanBeOne: Boolean,
    val maxCardCountSelection: DbMaxCardCountSelection,
    @Embedded
    val pointsRuleData: DbPointsRuleData,
    val gameFinished: Boolean
)
