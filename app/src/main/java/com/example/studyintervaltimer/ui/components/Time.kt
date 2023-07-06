package com.example.studyintervaltimer.ui.components

data class Time(var ms: Long)

fun Time.minus(value: Long): Time {
    return Time(ms.minus(value))
}

fun Time.getAsMs(): Long {
    return ms
}

fun Time.displayAsMinutes(): String {
    val minutes = ms / 1000 / 60
    val seconds = ms / 1000 % 60

    val minutesString = if (minutes < 10) "0$minutes" else "" + minutes
    val secondsString = if (seconds < 10) "0$seconds" else "" + seconds

    return "$minutesString:$secondsString"
}