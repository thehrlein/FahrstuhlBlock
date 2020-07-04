package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.ui_common.utils.FixedGridLayoutManager

@BindingAdapter("fixedColumnCount")
fun RecyclerView.setFixedColumnCount(columnCount: Int?) {
    if (layoutManager == null) {
        columnCount?.let { count ->
            layoutManager = FixedGridLayoutManager().apply {
                totalColumnCount = count
            }
        }
    }
}