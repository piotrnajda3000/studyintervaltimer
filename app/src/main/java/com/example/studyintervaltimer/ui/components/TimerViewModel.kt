package com.example.studyintervaltimer.ui.components

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyintervaltimer.ui.chainedTimers.ChainedTimersWorkoutDestination
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerViewModel(
    initialState: TimerUiState = TimerUiState((4000L)),
) : ViewModel() {
    private val _timerUiState = MutableStateFlow(initialState)
    val timerUiState = _timerUiState.asStateFlow()

    var elapsedTime: Long = 0

    fun resetTimer() {
        elapsedTime = 0
        _timerUiState.update {
            it.copy(
                isTimerRunning = false, remainingTimeMs = timerUiState.value.totalTime.getAsMs()

            )
        }
    }

    fun startTimer() {
        if (timerUiState.value.remainingTime.getAsMs() > 0) {
            _timerUiState.update {
                it.copy(isTimerRunning = true)
            }
        }
    }

    fun pauseTimer() {
        _timerUiState.update {
            it.copy(isTimerRunning = false)
        }
    }

    fun getProgress(): Float {
        return (elapsedTime.toDouble() / timerUiState.value.totalTime.getAsMs()
            .toDouble()).toFloat()
    }

    suspend fun tick(onTimerFinish: () -> Unit) {
        if (timerUiState.value.remainingTime.getAsMs() <= 0) {
            _timerUiState.update {
                it.copy(isTimerRunning = false)
            }
            onTimerFinish()
        }
        if (timerUiState.value.isTimerRunning) {
            delay(1000L)
            elapsedTime += 1000L
            _timerUiState.update {
                it.copy(
                    remainingTimeMs = timerUiState.value.remainingTime.getAsMs().minus(1000L)
                )
            }
        }

    }
}

