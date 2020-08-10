package com.tobiapplications.fahrstuhlblock.entities.models.game.result

data class BlockName(
    val name: String,
    val isDealer: Boolean,
    val isCurrentLeader: Boolean
) : BlockItem