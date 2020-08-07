package com.tobiapplications.fahrstuhlblock.entities.models.settings

sealed class SettingsData {

    class Player(val names: List<String>) : SettingsData()

    sealed class Cards : SettingsData() {
        object OneDeck : Cards()
        object TwoDecks: Cards()
        class Individual(val count: Int): Cards()
    }

    class Points(val pointsRuleData: PointsRuleData): SettingsData()
}