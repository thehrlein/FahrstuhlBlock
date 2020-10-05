package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.ui_common.R

@BindingAdapter("inputType", "gameFinished", requireAll = true)
fun ExtendedFloatingActionButton.setFabIconAndText(inputType: InputType?, gameFinished: Boolean?) {
    if (inputType == null || gameFinished == null) return

    text = when {
        gameFinished -> context.getString(R.string.block_results_fab_exit)
        inputType == InputType.TIPP -> context.getString(R.string.block_results_fab_add_prediction)
        inputType == InputType.RESULT -> context.getString(R.string.block_results_fab_add_results)
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
