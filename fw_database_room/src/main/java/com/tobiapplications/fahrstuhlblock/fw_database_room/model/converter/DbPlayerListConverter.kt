package com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter

import androidx.room.TypeConverter

private const val PLAYER_LIST_SEPARATOR = "___"

class DbPlayerListConverter {

    @TypeConverter
    fun fromString(value: String) : List<String> {
        return value.split(PLAYER_LIST_SEPARATOR)
    }

    @TypeConverter
    fun fromList(list: List<String>) : String {
        return list.joinToString(separator = PLAYER_LIST_SEPARATOR) { it }
    }
}