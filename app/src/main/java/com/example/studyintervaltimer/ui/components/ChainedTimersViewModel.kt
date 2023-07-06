package com.example.studyintervaltimer.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChainedTimersViewModel() : ViewModel() {
    private val _timersUiState = MutableStateFlow(ChainedTimersUiState())

    val timersUiState = _timersUiState.asStateFlow()

    var timerInstanceId = 1;

    fun addTimer(timer: TimerViewModel) {
        _timersUiState.value.timers.add(
            ChainedTimer(
                timer,
                timerInstanceId,
                onTimerFinish = { onTimerFinish() }
            )
        )
        timerInstanceId += 1
    }

    fun getTimer(no: Int): ChainedTimer? {
        if (no - 1 > timersUiState.value.timers.lastIndex) {
            return null
        }
        return timersUiState.value.timers[no - 1]
    }

    fun getCurrentTimer(): ChainedTimer {
        return getTimer(timersUiState.value.currentTimerNo)!!
    }

    private fun onTimerFinish() {
        if (timersUiState.value.currentTimerNo <= timersUiState.value.timers.size - 1) {
            viewModelScope.launch {
                _timersUiState.update {
                    it.copy(currentTimerNo = it.currentTimerNo.inc())
                }
                getCurrentTimer()?.timerViewModel?.startTimer()
            }
        }
    }

    fun pauseOrResume() {
        if (timersUiState.value.timers.filter { timer ->
                timer.getTimerUiState().value.isTimerRunning
            }.isEmpty()) {
            getCurrentTimer().timerViewModel.startTimer()
        } else {
            getCurrentTimer().timerViewModel.pauseTimer()
        }
    }



}
