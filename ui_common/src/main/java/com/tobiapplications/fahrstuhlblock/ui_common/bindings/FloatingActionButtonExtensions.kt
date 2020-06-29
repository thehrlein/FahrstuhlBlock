package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.ui_common.R

@BindingAdapter("text")
fun ExtendedFloatingActionButton.setText(inputType: InputType?) {
    if (inputType == null) return

    text = context.getString(when (inputType) {
        InputType.TIPP -> R.string.block_fab_add_prediction
        InputType.RESULT -> R.string.block_fab_add_results
    })
}