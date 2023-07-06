package com.example.studyintervaltimer.ui.components

import kotlinx.coroutines.flow.StateFlow

class ChainedTimer(
    var timerViewModel: TimerViewModel,
    var timerInstanceId: Int,
    var onTimerFinish: () -> Unit,
) {
    fun getTimerUiState(): StateFlow<TimerUiState> {
        return timerViewModel.timerUiState
    }

    suspend fun tick(currentTimerNo: Int) {
        if (currentTimerNo.equals(timerInstanceId)) {
            timerViewModel.tick(onTimerFinish)
        }
    }
}