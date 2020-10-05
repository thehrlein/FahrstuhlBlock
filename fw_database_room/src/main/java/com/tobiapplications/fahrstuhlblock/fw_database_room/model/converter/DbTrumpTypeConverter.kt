package com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter

import androidx.room.TypeConverter
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbTrumpType

class DbTrumpTypeConverter {

    @TypeConverter
    fun fromString(value: String): DbTrumpType {
        return DbTrumpType.valueOf(value.toUpperCase())
    }

    @TypeConverter
    fun fromType(trumpType: DbTrumpType): String {
        return trumpType.toString()
    }
}
