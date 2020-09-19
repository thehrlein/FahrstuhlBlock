package com.tobiapplications.fahrstuhlblock.ui_common.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getGameStartDate(millis: Long): String {
        val date = Date(millis)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        val formattedTime = timeFormat.format(date)
        return "$formattedDate\n$formattedTime"
    }
}
