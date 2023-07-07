package com.example.studyintervaltimer.ui.time.chainedTimer

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyintervaltimer.data.ModelsRepository
import com.example.studyintervaltimer.ui.time.timer.TimerDetails
import com.example.studyintervaltimer.ui.time.timer.TimerUiState
import com.example.studyintervaltimer.ui.time.timer.TimerViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChainedTimersViewModel(
    savedStateHandle: SavedStateHandle,
    private val modelsRepository: ModelsRepository,
) : ViewModel() {
    private val timersSetId: Long =
        checkNotNull(savedStateHandle[ChainedTimersDestination.workoutId])

    val uiState: StateFlow<ChainedTimersUiState> =
        modelsRepository.getTimerSetWithTimersStream(timersSetId)
            .filterNotNull()
            .map {
                ChainedTimersUiState(
                    setDetails = ChainedTimersSetDetails(
                        id = it.timerSet.timerSetId,
                        name = it.timerSet.name
                    ),
                    timerDetails = ChainedTimersDetails(
                        timers = it.timers.map { timer ->
                            TimerViewModel(
                                initialState = TimerUiState(
                                    timerDetails = TimerDetails(
                                        remainingTimeMs = timer.remainingTimeMs,
                                        totalTimeMs = timer.totalTimeMs,
                                        isTimerRunning = timer.isTimerRunning,
                                        elapsedTime = timer.elapsedTime,
                                        id = timer.timerId,
                                        timerSetId = timer.isInTimerSetId
                                    )
                                ),
                                modelsRepository = modelsRepository
                            )
                        }.toMutableList(),
                        currentTimerId = it.timerSet.currentTimerId,
                    )
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ChainedTimersUiState()
            )

    fun moveToNextTimer() {
        val currentTimer = getCurrentTimer()
        val nextTimer = getNextTimer()
        currentTimer.pauseTimer()
        if (nextTimer !== null) {
            viewModelScope.launch {
                modelsRepository.updateTimersSet(
                    uiState.value.copy(
                        timerDetails = uiState.value.timerDetails.copy(currentTimerId = uiState.value.timerDetails.currentTimerId.inc())
                    ).toTimersSet()
                )
            }
        }
    }

    fun moveToPrevTimer() {
        val currentTimer = getCurrentTimer()
        val prevTimer = getPrevTimer()
        currentTimer.resetTimer()
        if (prevTimer !== null) {
            prevTimer.resetTimer()
            viewModelScope.launch {
                modelsRepository.updateTimersSet(
                    uiState.value.copy(
                        timerDetails = uiState.value.timerDetails.copy(currentTimerId = prevTimer.timerUiState.value.timerDetails.id)
                    ).toTimersSet()
                )
            }
        }
    }

    fun onTimerFinish() {
        if (getNextTimer() != null) {
            viewModelScope.launch {
                modelsRepository.updateTimersSet(
                    uiState.value.copy(
                        timerDetails = uiState.value.timerDetails.copy(currentTimerId = uiState.value.timerDetails.currentTimerId + 1),
                    ).toTimersSet()
                )
                Log.d("hehe", getNextTimer()?.timerUiState!!.value.timerDetails.id.toString())
                getNextTimer()?.startTimer()
            }
        } else {
            viewModelScope.launch {
                uiState.value.timerDetails.timers.forEach {
                    it.resetTimer()
                }
                modelsRepository.updateTimersSet(
                    uiState.value.copy(
                        timerDetails = uiState.value.timerDetails.copy(currentTimerId = uiState.value.timerDetails.timers[0].timerUiState.value.timerDetails.id)
                    ).toTimersSet()
                )
            }
        }
    }

    private fun getTimer(no: Long): TimerViewModel? {
        return uiState.value.timerDetails.timers.find {
             it.timerUiState.value.timerDetails.id == no
        }
    }

    fun getCurrentTimer(): TimerViewModel {
        return getTimer(uiState.value.timerDetails.currentTimerId)!!
    }

    private fun getNextTimer(): TimerViewModel? {
        return getTimer(uiState.value.timerDetails.currentTimerId + 1)
    }

    private fun getPrevTimer(): TimerViewModel? {
        return getTimer(uiState.value.timerDetails.currentTimerId - 1)
    }
}
