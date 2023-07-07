package com.example.studyintervaltimer.data

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.studyintervaltimer.R
import com.example.studyintervaltimer.ui.components.TimerUiState
import com.example.studyintervaltimer.ui.components.TimerViewModel

object IntervalsDataSource {
    var classicPomodoro2 = Workout(title = R.string.classic_pomodoro,
    timers = mutableListOf(
        TimerViewModel(TimerUiState(25L * 60000)),
        TimerViewModel(TimerUiState(5L * 60000L)),
        TimerViewModel(TimerUiState(25L * 60000)),
        TimerViewModel(TimerUiState(5L * 60000L)),
        TimerViewModel(TimerUiState(25L * 60000L)),
        TimerViewModel(TimerUiState(5L * 60000L)),
        TimerViewModel(TimerUiState(25L * 60000L)),
        TimerViewModel(TimerUiState(20L * 60000L))
    )
    )
    var classicPomodoro = Workout(title = R.string.classic_pomodoro,
        timers = mutableListOf(
            TimerViewModel(TimerUiState(1L * 1000L)),
            TimerViewModel(TimerUiState(2L * 1000L)),
            TimerViewModel(TimerUiState(3L * 1000L)),
            TimerViewModel(TimerUiState(2L * 1000L)),
            TimerViewModel(TimerUiState(1L * 1000L)),
            TimerViewModel(TimerUiState(2L * 1000L)),
            TimerViewModel(TimerUiState(1L * 1000L)),
            TimerViewModel(TimerUiState(2L * 1000L))
        )
    )
}

@Entity
data class Workout(
    @StringRes val title: Int,
    val timers: MutableList<TimerViewModel>
)
