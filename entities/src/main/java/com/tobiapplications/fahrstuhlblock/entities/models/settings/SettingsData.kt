package com.tobiapplications.fahrstuhlblock.entities.models.settings

sealed class SettingsData {

    class Player(val names: List<String>) : SettingsData()

    sealed class Cards : SettingsData() {
        class OneDeck(
            val stopAtHighCard: Boolean,
            val firstRoundTipsCanBeOne: Boolean
        ) : Cards()
        class TwoDecks(
            val stopAtHighCard: Boolean,
            val firstRoundTipsCanBeOne: Boolean
        ) : Cards()
        class Individual(
            val count: Int,
            val stopAtHighCard: Boolean,
            val firstRoundTipsCanBeOne: Boolean
        ) : Cards()
    }

    class Points(val pointsRuleData: PointsRuleData) : SettingsData()
}
