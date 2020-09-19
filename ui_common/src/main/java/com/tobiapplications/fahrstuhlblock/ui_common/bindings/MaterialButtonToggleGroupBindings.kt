package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

@BindingAdapter("playerCount")
fun MaterialButtonToggleGroup.setPlayerCount(count: Int?) {
    when (count) {
        3 -> (getChildAt(0) as MaterialButton).isChecked = true
        4 -> (getChildAt(1) as MaterialButton).isChecked = true
        5 -> (getChildAt(2) as MaterialButton).isChecked = true
        6 -> (getChildAt(3) as MaterialButton).isChecked = true
        7 -> (getChildAt(4) as MaterialButton).isChecked = true
        8 -> (getChildAt(5) as MaterialButton).isChecked = true
    }
}
