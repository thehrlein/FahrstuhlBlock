package com.tobiapplications.fahrstuhlblock.ui_common.extension

import android.content.Context
import android.view.LayoutInflater


val Context.layoutInflater : LayoutInflater
    get() =  LayoutInflater.from(this)
