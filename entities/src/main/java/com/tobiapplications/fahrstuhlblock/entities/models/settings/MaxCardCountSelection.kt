package com.tobiapplications.fahrstuhlblock.entities.models.settings

enum class MaxCardCountSelection(val cards: Int) {

    ONE_DECK(32),
    TWO_DECKS(64),
    INDIVIDUAL(0)
}