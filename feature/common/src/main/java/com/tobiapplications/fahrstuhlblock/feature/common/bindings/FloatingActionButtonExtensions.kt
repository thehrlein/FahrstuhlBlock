package com.tobiapplications.fahrstuhlblock.feature.common.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.feature.common.R

@BindingAdapter("inputType", "gameFinished", requireAll = true)
fun ExtendedFloatingActionButton.setFabIconAndText(inputType: InputType?, gameFinished: Boolean?) {
    if (inputType == null || gameFinished == null) return

    text = when {
        gameFinished -> context.getString(com.tobiapplications.fahrstuhlblock.feature.common.R.string.block_results_fab_exit)
        inputType == InputType.TIPP -> context.getString(com.tobiapplications.fahrstuhlblock.feature.common.R.string.block_results_fab_add_prediction)
        inputType == InputType.RESULT -> context.getString(com.tobiapplications.fahrstuhlblock.feature.common.R.string.block_results_fab_add_results)
        else -> null
    }

    setIconResource(
        when {
            gameFinished -> R.drawable.ic_exit
            else -> R.drawable.ic_add
        }
    )

    extend()
}
