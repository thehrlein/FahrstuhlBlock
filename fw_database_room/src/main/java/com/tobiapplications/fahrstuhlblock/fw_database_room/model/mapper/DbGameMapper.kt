package com.tobiapplications.fahrstuhlblock.fw_database_room.model.mapper

import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.entities.models.settings.MaxCardCountSelection
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.classes.*
import com.tobiapplications.fahrstuhlblock.fw_database_room.model.entity.DbGameInfo

fun GameInfo.mapToDbData() = DbGameInfo(
    gameId = gameId,
    gameStartDate = gameStartDate,
    players = players.mapToDbData(),
    highCardCount = highCardCount,
    maxCardCountSelection = maxCardCountSelection.mapToDbData(),
    pointsRuleData = pointsRuleData.mapToDbData(),
    gameFinished = gameFinished
)

fun DbGameInfo.mapToData() = GameInfo(
    gameId = gameId,
    gameStartDate = gameStartDate,
    players = players.mapToData(),
    highCardCount = highCardCount,
    maxCardCountSelection = maxCardCountSelection.mapToData(),
    pointsRuleData = pointsRuleData.mapToData(),
    gameFinished = gameFinished
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
    round = round,
    cardCount = cardCount,
    playerTippData = playerTippData.map { it.mapToDbData() },
    playerResultData = playerResultData.map { it.mapToDbData() },
    trumpType = trumpType.mapToDbTyp()
)

fun DbRound.mapToData() = Round(
    round = round,
    cardCount = cardCount,
    playerTippData = playerTippData.map { it.mapToData() },
    playerResultData = playerResultData.map { it.mapToData() },
    trumpType = trumpType.mapTbTyp()
)

fun PlayerTippData.mapToDbData() = DbPlayerTippData(
    playerName = playerName,
    tipp = tipp
)

fun DbPlayerTippData.mapToData() = PlayerTippData(
    playerName = playerName,
    tipp = tipp
)

fun PlayerResultData.mapToDbData() = DbPlayerResultData(
    playerName = playerName,
    difference = difference,
    total = total,
    result = result
)

fun DbPlayerResultData.mapToData() = PlayerResultData(
    playerName = playerName,
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

fun MaxCardCountSelection.mapToDbData() = when (this) {
    MaxCardCountSelection.ONE_DECK -> DbMaxCardCountSelection.ONE_DECK
    MaxCardCountSelection.TWO_DECKS -> DbMaxCardCountSelection.TWO_DECKS
    MaxCardCountSelection.INDIVIDUAL -> DbMaxCardCountSelection.INDIVIDUAL
}

fun DbMaxCardCountSelection.mapToData() = when (this) {
    DbMaxCardCountSelection.ONE_DECK -> MaxCardCountSelection.ONE_DECK
    DbMaxCardCountSelection.TWO_DECKS -> MaxCardCountSelection.TWO_DECKS
    DbMaxCardCountSelection.INDIVIDUAL -> MaxCardCountSelection.INDIVIDUAL
}