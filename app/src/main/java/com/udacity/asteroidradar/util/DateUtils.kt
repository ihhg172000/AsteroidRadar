package com.udacity.asteroidradar.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedString(): String {
    return SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(this)
}

fun getToday(): Date {
    return Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
}

fun getWeekend(): Date {
    return Calendar.getInstance().apply {
        set(Calendar.DAY_OF_WEEK, 7)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
}