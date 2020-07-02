package com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbPlayerTippData
import java.lang.reflect.Type

class DbPlayerTippDataListConverter {

    @TypeConverter
    fun fromString(value: String) : List<DbPlayerTippData> {
        val listType: Type = object : TypeToken<List<DbPlayerTippData>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<DbPlayerTippData>) : String {
        return Gson().toJson(list)
    }
}