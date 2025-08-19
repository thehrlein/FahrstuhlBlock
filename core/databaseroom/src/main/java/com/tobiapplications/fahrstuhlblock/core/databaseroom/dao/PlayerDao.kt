package com.tobiapplications.fahrstuhlblock.core.databaseroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.entity.DbPlayer

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayerNames(dbPlayer: List<DbPlayer>)

    @Query("SELECT * FROM player_names")
    fun queryAllPlayerNames(): List<DbPlayer>
}
