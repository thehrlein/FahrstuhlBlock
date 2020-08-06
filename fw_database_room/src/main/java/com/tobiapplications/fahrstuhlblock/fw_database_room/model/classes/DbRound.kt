package com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes

import androidx.room.Embedded
import androidx.room.Entity
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType

@Entity(tableName = "game_rounds", primaryKeys = ["gameId", "round"])
data class DbRound(
    val gameId: Long,
    val round: Int,
    val cardCount: Int,
    val playerTippData: List<DbPlayerTippData>,
    val playerResultData: List<DbPlayerResultData>,
    val trumpType: DbTrumpType
)