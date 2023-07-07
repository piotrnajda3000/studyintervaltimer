package com.example.studyintervaltimer.ui.time

import com.example.studyintervaltimer.ui.time.timer.TimerUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface TickStrategy {
    fun onTimerFinish(): () -> Unit = { }
    suspend fun tick(
        _timerUiState: MutableStateFlow<TimerUiState>,
    )
}

class SingleTimerTickStrategy (
    private val onTimerFinish: () -> Unit = { }
) : TickStrategy {
    override fun onTimerFinish(): () -> Unit = onTimerFinish

    override suspend fun tick(
        _timerUiState: MutableStateFlow<TimerUiState>,
    ) {
        if (_timerUiState.value.timerDetails.remainingTime.getAsMs() <= 0) {
            _timerUiState.update {
                it.copy(timerDetails = it.timerDetails.copy(isTimerRunning = false))
            }
            onTimerFinish()
        }
        if (_timerUiState.value.timerDetails.isTimerRunning) {
            delay(1000L)
            _timerUiState.update {
                it.copy(
                    timerDetails = it.timerDetails.copy(
                        remainingTimeMs = it.timerDetails.remainingTime.getAsMs().minus(1000L),
                        elapsedTime = it.timerDetails.elapsedTime + 1000L
                    )
                )
            }
        }
    }
}

class ChainedTimerTickStrategy(
    onTimerFinish: () -> Unit,
    private val currentTimerId: Long
) : TickStrategy {
    private val singleTimerTickStrategy = SingleTimerTickStrategy(onTimerFinish)
    override suspend fun tick(
        _timerUiState: MutableStateFlow<TimerUiState>,
    ) {
        if (currentTimerId == _timerUiState.value.timerDetails.id) {
            singleTimerTickStrategy.tick(_timerUiState)
        }
    }
}
