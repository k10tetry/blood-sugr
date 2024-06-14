package com.k10tetry.bloodsugr.common

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.time.Duration.Companion.days

fun Double.round(): Double {
    val formatter = DecimalFormat("#.##")
    return formatter.format(this).toDouble()
}

fun Long.milliToTime(): String {
    val formatter = SimpleDateFormat("HH:mm")
    val date = Date(this)
    return formatter.format(date)
}

fun Long.milliToDate(): String {
    val formatter = SimpleDateFormat("dd MMM, yyyy")
    val date = Date(this)
    return formatter.format(date)
}

fun Long.milliToDay(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val day = calendar.get(Calendar.DAY_OF_WEEK)
    return day.toDays()
}

fun Int.toDays(): String {
    return if (this in 0..7) {
        val day = Days.entries[this - 1].name
        day.substring(0, 1) + day.substring(1).lowercase()
    } else {
        "Out of Range"
    }
}

enum class ErrorType {
    NETWORK_ERROR, PARSING_ERROR, OTHER
}

enum class Days {
    SUN, MON, TUE, WED, THU, FRI, SAT
}
