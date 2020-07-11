package com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper

import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.*
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity.DbGameInfo

fun GameInfo.mapToDbData() = DbGameInfo(
    gameId = 0,
    players = players.mapToDbData(),
    highCardCount = highCardCount,
    pointsRuleData = pointsRuleData.mapToDbData()
)

fun DbGameInfo.mapToData() = GameInfo(
    gameId = gameId,
    players = players.mapToData(),
    highCardCount = highCardCount,
    pointsRuleData = pointsRuleData.mapToData()
)

fun PlayerSettingsData.mapToDbData() = DbPlayerSettingsData(
    names = names
)

fun DbPlayerSettingsData.mapToData() = PlayerSettingsData(
    names = names
)

fun PointsRuleData.mapToDbData() = DbPointsRuleData(
    correctPoints = correctPoints,
    pointsPerStitch = pointsPerStitch,
    minusPointsPerStitch = minusPointsPerStitch,
    pointsIfPredictionFalse = pointsIfPredictionFalse
)

fun DbPointsRuleData.mapToData() = PointsRuleData(
    correctPoints = correctPoints,
    pointsPerStitch = pointsPerStitch,
    minusPointsPerStitch = minusPointsPerStitch,
    pointsIfPredictionFalse = pointsIfPredictionFalse
)

fun Round.mapToDbData(gameId: Long) = DbRound(
    gameId = gameId,
    card = card,
    playerTippData = playerTippData.map { it.mapToDbData() },
    playerResultData = playerResultData.map { it.mapToDbData() },
    trumpType = trumpType.mapToDbTyp()
)

fun DbRound.mapToData() = Round(
    card = card,
    playerTippData = playerTippData.map { it.mapToData() },
    playerResultData = playerResultData.map { it.mapToData() },
    trumpType = trumpType.mapTbTyp()
)

fun PlayerTippData.mapToDbData() = DbPlayerTippData(
    tipp = tipp
)

fun DbPlayerTippData.mapToData() = PlayerTippData(
    tipp = tipp
)

fun PlayerResultData.mapToDbData() = DbPlayerResultData(
    difference = difference,
    total = total,
    result = result
)

fun DbPlayerResultData.mapToData() = PlayerResultData(
    difference = difference,
    total = total,
    result = result
)

fun DbGame.mapToData() = Game(
    gameInfo = gameInfo.mapToData(),
    rounds = rounds.map { it.mapToData() }
)

fun TrumpType.mapToDbTyp() = when (this) {
    TrumpType.NONE -> DbTrumpType.NONE
    TrumpType.CLUB -> DbTrumpType.CLUB
    TrumpType.SPADE -> DbTrumpType.SPADE
    TrumpType.HEART -> DbTrumpType.HEART
    TrumpType.DIAMOND -> DbTrumpType.DIAMOND
}

fun DbTrumpType.mapTbTyp() = when (this) {
    DbTrumpType.NONE -> TrumpType.NONE
    DbTrumpType.CLUB -> TrumpType.CLUB
    DbTrumpType.SPADE -> TrumpType.SPADE
    DbTrumpType.HEART -> TrumpType.HEART
    DbTrumpType.DIAMOND -> TrumpType.DIAMOND
}