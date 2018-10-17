package com.takeiteasy.vip.vilmatetest.presentation.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    const val DATE_TIME_FORMAT = "dd/MM/yyyy\n'at' HH:mm"

    fun formatDate(date: Date, format: String): String
            = SimpleDateFormat(format, Locale.getDefault()).format(date)
}