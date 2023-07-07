package com.example.studyintervaltimer.ui.components

import android.util.Log
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
            Log.d("tick", "tick")
            timerViewModel.tick(onTimerFinish)
        }
    }

  
}