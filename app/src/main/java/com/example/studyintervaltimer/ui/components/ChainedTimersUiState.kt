package com.example.studyintervaltimer.ui.components

import androidx.compose.runtime.mutableStateListOf

data class ChainedTimersUiState(
    val currentTimerNo: Int = 1,
    val timers: MutableList<ChainedTimer> = mutableStateListOf(),
)

