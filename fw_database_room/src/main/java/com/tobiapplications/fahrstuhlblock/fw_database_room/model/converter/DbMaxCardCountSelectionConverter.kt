package com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter

import androidx.room.TypeConverter
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbMaxCardCountSelection
import java.util.Locale

class DbMaxCardCountSelectionConverter {

    @TypeConverter
    fun fromString(value: String): DbMaxCardCountSelection {
        return DbMaxCardCountSelection.valueOf(value.uppercase(Locale.getDefault()))
    }

    @TypeConverter
    fun fromSelection(selection: DbMaxCardCountSelection): String {
        return selection.toString()
    }
}
