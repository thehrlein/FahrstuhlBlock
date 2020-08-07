package com.tobiapplications.fahrstuhlblock.fw_database_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tobiapplications.fahrstuhlblock.fw_database_room.dao.GameDao
import com.tobiapplications.fahrstuhlblock.fw_database_room.dao.PlayerDao
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbRound
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter.*
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity.DbGameInfo
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity.DbPlayer

@Database(
    entities = [DbGameInfo::class, DbRound::class, DbPlayer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        DbPlayerListConverter::class,
        DbPlayerTippDataListConverter::class,
        DbPlayerResultDataListConverter::class,
        DbTrumpTypeConverter::class,
        DbMaxCardCountSelectionConverter::class
    ]
)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun playerDao(): PlayerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getInstance(context: Context): GameDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "persistent_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}