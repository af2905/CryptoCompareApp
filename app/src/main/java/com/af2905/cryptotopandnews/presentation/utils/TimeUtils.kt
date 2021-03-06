package com.af2905.cryptotopandnews.presentation.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class TimeUtils {

    companion object {
        fun convertTimestampToTime(timestamp: Long): String {
            val stamp = Timestamp(timestamp * 1000)
            val date = Date(stamp.time)
            val pattern = "EEEE, HH:mm"
            val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            dateFormat.timeZone = TimeZone.getDefault()
            return dateFormat.format(date)
        }
    }
}
