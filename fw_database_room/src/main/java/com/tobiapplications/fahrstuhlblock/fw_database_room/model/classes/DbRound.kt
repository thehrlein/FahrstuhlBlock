package com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "game_rounds", primaryKeys = ["gameId", "card"])
data class DbRound(
    val gameId: Long,
    val card: Int,
    val playerTippData: List<DbPlayerTippData>,
    val playerResultData: List<DbPlayerResultData>
)