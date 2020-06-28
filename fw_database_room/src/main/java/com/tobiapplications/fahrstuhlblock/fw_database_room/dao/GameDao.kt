package com.tobiapplications.fahrstuhlblock.fw_database_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity.DbGame

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(dbPlayer: DbGame): Long

    @Query("SELECT * FROM GAME_DATABASE WHERE id = :gameId")
    fun getGame(gameId: Long): DbGame

}