package com.tobiapplications.fahrstuhlblock.entities.models.firebase

object TrackingConstants {

    // Player Settings
    private const val EVENT_PLAYER_SETTINGS_PLAYER_COUNT = "player_count_%d"

    fun getPlayerSettingsEvent(count: Int) =
        String.format(EVENT_PLAYER_SETTINGS_PLAYER_COUNT, count)

    // Game Rules
    private const val EVENT_GAME_RULES_HIGH_CARD = "high_card_%d"
    private const val EVENT_STOP_ELEVATOR_AT_HIGH_CARD_TRUE = "stop_elevator_at_high_card_true"
    private const val EVENT_STOP_ELEVATOR_AT_HIGH_CARD_FALSE =
        "stop_elevator_at_high_card_false"

    fun getGameRulesHighCardEvent(highCard: Int) =
        String.format(EVENT_GAME_RULES_HIGH_CARD, highCard)
    fun getGameRulesStopElevatorAtHighCardEvent(value: Boolean) = if (value) EVENT_STOP_ELEVATOR_AT_HIGH_CARD_TRUE else EVENT_STOP_ELEVATOR_AT_HIGH_CARD_FALSE

    // Point Rules
    private const val EVENT_POINT_RULES_CORRECT_PREDICTION = "points_correct_prediction_%d"
    private const val EVENT_POINT_RULES_PLUS_POINTS_PER_STITCH = "points_plus_per_stitch_%d"
    private const val EVENT_POINT_RULES_MINUS_POINTS_PER_STITCH = "points_minus_per_stitch_%d"
    private const val EVENT_POINT_RULES_POINTS_FOR_WRONG_PREDICTON_TRUE = "points_for_wrong_prediction_true"
    private const val EVENT_POINT_RULES_POINTS_FOR_WRONG_PREDICTON_FALSE =
        "points_for_wrong_prediction_false"

    fun getPointRulesCorrectPredictionEvent(points: Int) =
        String.format(EVENT_POINT_RULES_CORRECT_PREDICTION, points)

    fun getPointRulesPlusPointsEvent(points: Int) =
        String.format(EVENT_POINT_RULES_PLUS_POINTS_PER_STITCH, points)

    fun getPointRulesMinusPointsEvent(points: Int) =
        String.format(EVENT_POINT_RULES_MINUS_POINTS_PER_STITCH, points)

    fun getPointRulesPointsForWrongPredictionEvent(value: Boolean) = if (value) EVENT_POINT_RULES_POINTS_FOR_WRONG_PREDICTON_TRUE else EVENT_POINT_RULES_POINTS_FOR_WRONG_PREDICTON_FALSE

    // Trump Selection
    private const val EVENT_TRUMP_SELECTION_AUTO_SHOW_DIALOG_TRUE = "trump_selection_auto_show_dialog_true"
    private const val EVENT_TRUMP_SELECTION_AUTO_SHOW_DIALOG_FALSE =
        "trump_selection_auto_show_dialog_false"

    fun getTrumpSelectionAutoShowDialogEvent(value: Boolean) = if (value) EVENT_TRUMP_SELECTION_AUTO_SHOW_DIALOG_TRUE else EVENT_TRUMP_SELECTION_AUTO_SHOW_DIALOG_FALSE

    // About
    const val EVENT_OPEN_PLAYSTORE_FAHRSTUHL_BLOCK = "play_store_open_fahrstuhl_block"
    const val EVENT_OPEN_PLAYSTORE_WIZARD_BLOCK = "play_store_open_wizard_block"
    const val EVENT_OPEN_PLAYSTORE_MOVIEBASE = "play_store_open_moviebase"
}