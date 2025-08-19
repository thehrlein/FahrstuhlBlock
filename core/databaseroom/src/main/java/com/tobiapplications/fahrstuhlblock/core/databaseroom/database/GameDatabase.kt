package com.tobiapplications.fahrstuhlblock.core.databaseroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tobiapplications.fahrstuhlblock.core.databaseroom.dao.GameDao
import com.tobiapplications.fahrstuhlblock.core.databaseroom.dao.PlayerDao
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.classes.DbRound
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.converter.*
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.entity.DbGameInfo
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.entity.DbPlayer

@Database(
    entities = [DbGameInfo::class, DbRound::class, DbPlayer::class],
    version = 2,
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
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
