package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.databinding.BindingAdapter
import com.tobiapplications.fahrstuhlblock.ui_common.R

@BindingAdapter("options")
fun AppCompatAutoCompleteTextView.setOptions(items: ArrayList<String>?) {
    items?.let {
        setAdapter(ArrayAdapter(context, R.layout.item_auto_complete_text_row, it))
    }
}