package com.tobiapplications.fahrstuhlblock.core.presentation.block.results

import com.tobiapplications.fahrstuhlblock.core.entities.models.game.result.TrumpType

interface BlockResultsInteractions {

    fun onTrumpClicked(trumpType: TrumpType)
}
