package com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbPlayerSettingsData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbPointsRuleData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbRound

@Entity(tableName = "game_database")
data class DbGame(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @Embedded
    val players: DbPlayerSettingsData,
    val highCardCount: Int,
    @Embedded
    val pointsRuleData: DbPointsRuleData,
    val rounds: List<DbRound>
)