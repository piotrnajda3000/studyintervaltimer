package com.example.studyintervaltimer.ui.time.chainedTimer

import androidx.compose.runtime.mutableStateListOf
import com.example.studyintervaltimer.data.TimersSet
import com.example.studyintervaltimer.ui.time.timer.TimerViewModel

data class ChainedTimersUiState(
    val setDetails: ChainedTimersSetDetails = ChainedTimersSetDetails(),
    val timerDetails: ChainedTimersDetails = ChainedTimersDetails(),
)

fun ChainedTimersUiState.toTimersSet(): TimersSet {
    return TimersSet(
        currentTimerId = this.timerDetails.currentTimerId,
        timerSetId = this.setDetails.id,
        name = this.setDetails.name
    )
}

data class ChainedTimersSetDetails(
    val id: Long = 0,
    val name: String = "Interval $id",
)

data class ChainedTimersDetails(
    //    val timers: MutableList<TimerUiState> = mutableStateListOf(),
    val timers: MutableList<TimerViewModel> = mutableStateListOf(),
    val currentTimerId: Long = 1,
)

