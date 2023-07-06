package com.example.studyintervaltimer.ui.components

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TimerViewModel(initialState: TimerUiState = TimerUiState((DEFAULT_TIME))) : ViewModel() {
    companion object {
        private const val DEFAULT_TIME = 100L * 10L
    }

    private val _timerUiState = MutableStateFlow(initialState)
    val timerUiState = _timerUiState.asStateFlow()

    var elapsedTime: Long = 0

    fun resetTimer() {
        _timerUiState.value = TimerUiState(DEFAULT_TIME)
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

    suspend fun tick(onTimerFinish: () -> Unit) {
        if (_timerUiState.value.remainingTime.getAsMs() <= 0) {
            _timerUiState.update {
                it.copy(isTimerRunning = false)
            }
            onTimerFinish()
        }

        if (_timerUiState.value.isTimerRunning) {
            delay(1000L)
            elapsedTime += 1000L
            _timerUiState.update {
                it.copy(remainingTimeMs = it.remainingTime.getAsMs().minus(1000L))
            }
        }

    }
}

