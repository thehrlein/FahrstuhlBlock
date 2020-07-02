package com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper

import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
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
    playerResultData = playerResultData.map { it.mapToDbData() }
)

fun DbRound.mapToData() = Round(
    card = card,
    playerTippData = playerTippData.map { it.mapToData() },
    playerResultData = playerResultData.map { it.mapToData() }
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