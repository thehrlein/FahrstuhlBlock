package com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_names")
data class DbPlayer(
    @PrimaryKey
    val name: String

)