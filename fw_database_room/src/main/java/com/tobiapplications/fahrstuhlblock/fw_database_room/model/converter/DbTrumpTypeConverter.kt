package com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter

import androidx.room.TypeConverter
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbTrumpType
import java.util.Locale

class DbTrumpTypeConverter {

    @TypeConverter
    fun fromString(value: String): DbTrumpType {
        return DbTrumpType.valueOf(value.uppercase(Locale.getDefault()))
    }

    @TypeConverter
    fun fromType(trumpType: DbTrumpType): String {
        return trumpType.toString()
    }
}
