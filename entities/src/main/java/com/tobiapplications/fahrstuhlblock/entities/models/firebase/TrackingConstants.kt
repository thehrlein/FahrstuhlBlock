package com.tobiapplications.fahrstuhlblock.entities.models.firebase

object TrackingConstants {

    // Player Settings
    const val EVENT_PLAYER_SETTINGS_PLAYER_COUNT = "player_settings_player_count"
    const val PARAM_PLAYER_COUNT = "player_count"

    // Game Rules
    const val EVENT_GAME_RULES_HIGH_CARD = "game_rules_high_card"
    const val PARAM_HIGH_CARD = "high_card_count"

    // Point Rules
    const val EVENT_POINT_RULES_CORRECT_PREDICTION = "point_rules_correct_prediction"
    const val EVENT_POINT_RULES_PLUS_POINTS_PER_STITCH = "point_rules_plus_points_per_stitch"
    const val EVENT_POINT_RULES_MINUS_POINTS_PER_STITCH = "point_rules_minus_points_per_stitch"
    const val EVENT_POINT_RULES_POINTS_FOR_WRONG_PREDICTON = "point_rules_points_for_wrong_prediction"
    const val PARAM_CORRECT_PREDICTION = "correct_prediction_points"
    const val PARAM_PLUS_POINTS = "plus_points"
    const val PARAM_MINUS_POINTS= "minus_points"
    const val PARAM_POINTS_FOR_WRONG_PREDICTION = "points_for_wrong_prediction"

    // Trump Selection
    const val EVENT_TRUMP_SELECTION_AUTO_SHOW_DIALOG = "trump_selection_auto_show_dialog"
    const val PARAM_AUTO_SHOW_DIALOG = "auto_show_dialog"
}