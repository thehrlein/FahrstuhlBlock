package com.tobiapplications.fahrstuhlblock.entities.models.game.result

import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType

data class BlockItemData(
    val items: List<BlockItem>,
    val inputType: InputType,
    val columnCount: Int
)