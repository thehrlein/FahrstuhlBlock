package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.ui_common.R

@BindingAdapter("inputType", "summedInputs", "cardCount")
fun Button.setButtonText(inputType: InputType?, summedInputs: Int?, cardCount: Int?) {
    val suffix = if (summedInputs != null && cardCount != null) {
        context.getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.block_input_summed_inputs, summedInputs, cardCount)
    } else {
        context.getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.general_empty_string)
    }

    text = context.getString(if (inputType == InputType.TIPP) {
        R.string.block_input_enter_tipps
    } else {
        R.string.block_input_enter_results
    }, suffix)
}
