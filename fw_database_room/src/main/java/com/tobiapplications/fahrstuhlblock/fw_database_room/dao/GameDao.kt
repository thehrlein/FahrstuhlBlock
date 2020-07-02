package com.tobiapplications.fahrstuhlblock.fw_database_room.dao

import androidx.room.*
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbGame
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbRound
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity.DbGameInfo

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameInfo(dbGameInfo: DbGameInfo): Long

    @Transaction
    @Query("SELECT * FROM GAME_DATABASE WHERE gameId = :gameId")
    fun getGame(gameId: Long) : DbGame

    @Query("SELECT * FROM GAME_ROUNDS WHERE gameId = :gameId")
    fun getRounds(gameId: Long) : List<DbRound>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRound(dbRound: DbRound) : Long
}