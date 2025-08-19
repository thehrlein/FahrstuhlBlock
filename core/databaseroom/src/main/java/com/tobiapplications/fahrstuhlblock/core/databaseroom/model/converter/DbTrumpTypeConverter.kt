package com.tobiapplications.fahrstuhlblock.core.databaseroom.model.converter

import androidx.room.TypeConverter
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.classes.DbTrumpType
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
