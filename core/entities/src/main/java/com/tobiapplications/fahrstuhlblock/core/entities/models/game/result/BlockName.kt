package com.tobiapplications.fahrstuhlblock.core.entities.models.game.result

data class BlockName(
    val name: String,
    val isDealer: Boolean,
    val isCurrentLeader: Boolean
) : BlockItem
