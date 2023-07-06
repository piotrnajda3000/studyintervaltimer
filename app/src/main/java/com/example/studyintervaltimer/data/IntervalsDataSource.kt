package com.example.studyintervaltimer.data

import com.example.studyintervaltimer.ui.components.TimerUiState
import com.example.studyintervaltimer.ui.components.TimerViewModel

object IntervalsDataSource {
    var testing = listOf(
        TimerViewModel(TimerUiState(100L * 10L)),
        TimerViewModel(TimerUiState(100L * 20L)),
        TimerViewModel(TimerUiState(100L * 40L)),
        TimerViewModel(TimerUiState(100L * 20L)),
        TimerViewModel(TimerUiState(100L * 10L)),
        TimerViewModel(TimerUiState(100L * 30L)),
        TimerViewModel(TimerUiState(100L * 10L)),
        TimerViewModel(TimerUiState(100L * 20L)),
    )
}
