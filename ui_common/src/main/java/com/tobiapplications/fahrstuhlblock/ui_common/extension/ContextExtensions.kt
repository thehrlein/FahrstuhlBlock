package com.tobiapplications.fahrstuhlblock.ui_common.extension

import android.content.Context
import android.view.LayoutInflater


fun Context.getLayoutInflater() : LayoutInflater {
    return LayoutInflater.from(this)
}