package com.tobiapplications.fahrstuhlblock.core.databaseroom.model.classes

import androidx.room.Embedded
import androidx.room.Relation
import com.tobiapplications.fahrstuhlblock.core.databaseroom.model.entity.DbGameInfo

data class DbGame(
    @Embedded
    val gameInfo: DbGameInfo,
    @Relation(
        parentColumn = "gameId",
        entityColumn = "gameId"
    )
    val rounds: List<DbRound>
)
