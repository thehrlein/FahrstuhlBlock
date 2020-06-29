package com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes

data class DbRound(
    val card: Int,
    val playerTippData: List<DbPlayerTippData>,
    val playerResultData: List<DbPlayerResultData>
)