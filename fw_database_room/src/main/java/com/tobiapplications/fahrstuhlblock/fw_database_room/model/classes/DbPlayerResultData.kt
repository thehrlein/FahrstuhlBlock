package com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes

data class DbPlayerResultData(
    val playerName: String,
    val result: Int,
    val difference: Int,
    val total: Int
)
