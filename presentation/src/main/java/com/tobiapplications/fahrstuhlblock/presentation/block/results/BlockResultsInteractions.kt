package com.tobiapplications.fahrstuhlblock.presentation.block.results

import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType

interface BlockResultsInteractions {

    fun onTrumpClicked(trumpType: TrumpType)
}
