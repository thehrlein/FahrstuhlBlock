package com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbPlayerResultData
import java.lang.reflect.Type

class DbPlayerResultDataListConverter {

    @TypeConverter
    fun fromString(value: String): List<DbPlayerResultData> {
        val listType: Type = object : TypeToken<List<DbPlayerResultData>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<DbPlayerResultData>): String {
        return Gson().toJson(list)
    }
}