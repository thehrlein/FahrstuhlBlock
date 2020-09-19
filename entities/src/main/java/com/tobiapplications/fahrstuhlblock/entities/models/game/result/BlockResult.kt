package com.tobiapplications.fahrstuhlblock.entities.models.game.result

data class BlockResult(
    val player: String,
    val round: Int,
    val tipp: Int,
    val result: Int?,
    val difference: Int?,
    val total: Int?,
    val colorized: Boolean
) : BlockItem
