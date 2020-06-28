package com.tobiapplications.fahrstuhlblock.ui_common.extension

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.DimenRes
import kotlin.math.roundToInt


val Context.layoutInflater : LayoutInflater
    get() =  LayoutInflater.from(this)

fun Context.getDimen(@DimenRes dimenRes: Int) : Int {
    return resources.getDimension(dimenRes).roundToInt()
}

fun Context.getExactDimen(@DimenRes dimenRes: Int) : Int {
    return (resources.getDimension(dimenRes) / resources.displayMetrics.density).roundToInt()
}