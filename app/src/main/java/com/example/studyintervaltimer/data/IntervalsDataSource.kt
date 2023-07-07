package com.example.studyintervaltimer.data

import androidx.annotation.StringRes
import androidx.room.Entity
import com.example.studyintervaltimer.R
import com.example.studyintervaltimer.ui.time.timer.TimerDetails
import com.example.studyintervaltimer.ui.time.timer.TimerUiState

object IntervalsDataSource {
    var classicPomodoro = TimerSetWithTimers(
        timersSet = TimersSet(
            name = "Classic Pomodoro",
            currentTimerId = 1,
        ),
        timers = mutableListOf(
            TimerUiState(TimerDetails(id = 1, totalTimeMs = 25L * 60000)),
            TimerUiState(TimerDetails(id = 2, totalTimeMs = 5L * 60000L)),
            TimerUiState(TimerDetails(id = 3, totalTimeMs = 25L * 60000)),
            TimerUiState(TimerDetails(id = 4, totalTimeMs = 5L * 60000L)),
            TimerUiState(TimerDetails(id = 5, totalTimeMs = 25L * 60000L)),
            TimerUiState(TimerDetails(id = 6, totalTimeMs = 5L * 60000L)),
            TimerUiState(TimerDetails(id = 7, totalTimeMs = 25L * 60000L)),
            TimerUiState(TimerDetails(id = 8, totalTimeMs = 20L * 60000L))
        )
    )
}

data class TimerSetWithTimers(val timersSet: TimersSet, val timers: MutableList<TimerUiState>)
