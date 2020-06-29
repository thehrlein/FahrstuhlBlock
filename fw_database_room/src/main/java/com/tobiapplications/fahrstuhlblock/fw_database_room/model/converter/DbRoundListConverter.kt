package com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Round
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbRound
import java.lang.reflect.Type

class DbRoundListConverter {

    @TypeConverter
    fun fromString(value: String) : List<DbRound> {
        val listType: Type = object : TypeToken<List<DbRound>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<DbRound>) : String {
        return Gson().toJson(list)
    }
}