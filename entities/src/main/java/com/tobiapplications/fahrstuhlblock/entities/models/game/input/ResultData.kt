package com.tobiapplications.fahrstuhlblock.entities.models.game.input

data class ResultData(
    val playerName: String,
    val tipp: Int,
    val result: Int,
    val previousTotal: Int
)