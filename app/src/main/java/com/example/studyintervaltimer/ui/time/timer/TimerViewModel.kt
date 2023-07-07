package com.example.studyintervaltimer.ui.time.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyintervaltimer.data.ModelsRepository
import com.example.studyintervaltimer.ui.time.TickStrategy
import com.example.studyintervaltimer.ui.time.getAsMs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerViewModel(
    initialState: TimerUiState = TimerUiState(),
    private var modelsRepository: ModelsRepository,
) : ViewModel() {
    private val _timerUiState = MutableStateFlow(initialState)
    val timerUiState = _timerUiState.asStateFlow()

    fun resetTimer() {
        viewModelScope.launch {
            modelsRepository.updateTimer(
            timerUiState.value.copy(
                timerDetails = timerUiState.value.timerDetails.copy(
                    isTimerRunning = false,
                    remainingTimeMs = timerUiState.value.timerDetails.totalTime.getAsMs(),
                    elapsedTime = 0L
                )
            ).toTimer())
        }
    }

    fun startTimer() {
        if (timerUiState.value.timerDetails.remainingTime.getAsMs() > 0) {
            viewModelScope.launch {
                timerUiState.value.copy(
                    timerDetails = timerUiState.value.timerDetails.copy(
                        isTimerRunning = true
                    )
                )
            }
        }
    }

    fun pauseTimer() {
        viewModelScope.launch {
            modelsRepository.updateTimer(
                timerUiState.value.copy(
                    timerDetails =
                    timerUiState.value.timerDetails.copy(
                        isTimerRunning = false
                    )
                ).toTimer()
            )
        }
    }

    fun toggleTimer() {
        viewModelScope.launch {
            modelsRepository.updateTimer(
                timerUiState.value.copy(
                    timerDetails = timerUiState.value.timerDetails.copy(
                        isTimerRunning = !timerUiState.value.timerDetails.isTimerRunning
                    )
                ).toTimer())
        }
    }

    fun getProgress(): Float {
        return (timerUiState.value.timerDetails.elapsedTime.toDouble() / timerUiState.value.timerDetails.totalTime.getAsMs()
            .toDouble()).toFloat()
    }

    suspend fun tick(tickStrategy: TickStrategy) {
        tickStrategy.tick(_timerUiState, modelsRepository = modelsRepository)
    }
}

