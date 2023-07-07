package com.example.studyintervaltimer.ui.components

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChainedTimersViewModel(
) : ViewModel() {
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

    private fun getTimer(no: Int): ChainedTimer? {
        if (no - 1 > timersUiState.value.timers.lastIndex || no - 1 < 0) {
            return null
        }
        return timersUiState.value.timers[(no - 1)]
    }

    private fun getCurrentTimer(): ChainedTimer {
        return getTimer(timersUiState.value.currentTimerNo)!!
    }

    fun setHaveTimersBeenInitialized(v: Boolean) {
        _timersUiState.update {
            it.copy(haveTimersBeenInitialized = v)
        }
    }

    private fun getNextTimer(): ChainedTimer? {
        return getTimer(timersUiState.value.currentTimerNo + 1)
    }

    private fun getPrevTimer(): ChainedTimer? {
        return getTimer(timersUiState.value.currentTimerNo - 1)
    }

    fun nextTimer() {
        val currentTimer = getCurrentTimer()
        val nextTimer = getNextTimer()
        currentTimer.timerViewModel.pauseTimer()
        if (nextTimer !== null) {
            _timersUiState.update {
                it.copy(currentTimerNo = it.currentTimerNo + 1)

            }
        }
    }

    fun prevTimer() {
        val currentTimer = getCurrentTimer()
        val prevTimer = getPrevTimer()
        currentTimer.timerViewModel.pauseTimer()
        if (prevTimer !== null) {
            prevTimer.timerViewModel.resetTimer()
            _timersUiState.update {
                it.copy(currentTimerNo = prevTimer.timerInstanceId)
            }
        }
    }

    private fun onTimerFinish() {
        if (timersUiState.value.currentTimerNo <= timersUiState.value.timers.size - 1) {
            _timersUiState.update {
                it.copy(currentTimerNo = it.currentTimerNo.inc())

            }
            getCurrentTimer().timerViewModel.startTimer()
        } else {
            timersUiState.value.timers.forEach {
                it.timerViewModel.resetTimer()
            }
            _timersUiState.update {
                it.copy(currentTimerNo = 1)
            }
        }
    }

    fun pauseOrResume() {
        if (timersUiState.value.timers.none { timer ->
                timer.getTimerUiState().value.isTimerRunning
        }) {
            getCurrentTimer().timerViewModel.startTimer()
        } else {
            getCurrentTimer().timerViewModel.pauseTimer()
        }
    }
}
