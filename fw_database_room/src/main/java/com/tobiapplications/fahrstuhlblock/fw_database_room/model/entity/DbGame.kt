package com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_database")
data class DbGame(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val players: List<String>
)