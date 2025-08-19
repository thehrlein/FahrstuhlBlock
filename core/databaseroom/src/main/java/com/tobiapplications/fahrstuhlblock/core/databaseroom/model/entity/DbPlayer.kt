package com.tobiapplications.fahrstuhlblock.core.databaseroom.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_names")
data class DbPlayer(
    @PrimaryKey
    val name: String

)
