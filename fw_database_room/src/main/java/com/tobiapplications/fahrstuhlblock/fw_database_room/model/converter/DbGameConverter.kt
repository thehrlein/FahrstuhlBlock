package com.tobiapplications.fahrstuhlblock.fw_database_room.model.converter

import com.tobiapplications.fahrstuhlblock.entities.models.game.Game
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbPlayerSettingsData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.DbPointsRuleData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity.DbGame

fun Game.mapToDbData() = DbGame(
    id = 0,
    players = players.mapToDbData(),
    highCardCount = highCardCount,
    pointsRuleData = pointsRuleData.mapToDbData()
)

fun DbGame.mapToData() = Game(
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