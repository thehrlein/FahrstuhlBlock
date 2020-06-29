package com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper

import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerTippData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Round
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.*
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity.DbGame

fun Game.mapToDbData() = DbGame(
    id = 0,
    players = players.mapToDbData(),
    highCardCount = highCardCount,
    pointsRuleData = pointsRuleData.mapToDbData(),
    rounds = rounds.map { it.mapToDbData() }
)

fun DbGame.mapToData() = Game(
    players = players.mapToData(),
    highCardCount = highCardCount,
    pointsRuleData = pointsRuleData.mapToData(),
    rounds = rounds.map { it.mapToData() }
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

fun Round.mapToDbData() = DbRound(
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