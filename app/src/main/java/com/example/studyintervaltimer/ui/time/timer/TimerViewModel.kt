package com.example.studyintervaltimer.ui.time.timer

import androidx.lifecycle.ViewModel
import com.example.studyintervaltimer.ui.time.TickStrategy
import com.example.studyintervaltimer.ui.time.getAsMs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TimerViewModel(
    initialState: TimerUiState = TimerUiState(),
) : ViewModel() {
    private val _timerUiState = MutableStateFlow(initialState)
    val timerUiState = _timerUiState.asStateFlow()

    fun resetTimer() {
        _timerUiState.update {
            it.copy(
                timerDetails = it.timerDetails.copy(
                    isTimerRunning = false,
                    remainingTimeMs = it.timerDetails.totalTime.getAsMs(),
                    elapsedTime = 0L
                )
            )
        }
    }

    fun startTimer() {
        if (timerUiState.value.timerDetails.remainingTime.getAsMs() > 0) {
            _timerUiState.update {
                it.copy(timerDetails = it.timerDetails.copy(isTimerRunning = true))
            }
        }
    }

    fun pauseTimer() {
        _timerUiState.update {
            it.copy(timerDetails = it.timerDetails.copy(isTimerRunning = false))
        }
    }

    fun toggleTimer() {
        _timerUiState.update {
            it.copy(timerDetails = it.timerDetails.copy(isTimerRunning = !it.timerDetails.isTimerRunning))
        }
    }

    fun getProgress(): Float {
        return (timerUiState.value.timerDetails.elapsedTime.toDouble() / timerUiState.value.timerDetails.totalTime.getAsMs()
            .toDouble()).toFloat()
    }

    suspend fun tick(tickStrategy: TickStrategy) {
        tickStrategy.tick(_timerUiState)
    }
}

