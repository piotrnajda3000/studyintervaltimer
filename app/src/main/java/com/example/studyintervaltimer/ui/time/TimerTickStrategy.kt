package com.example.studyintervaltimer.ui.time

import android.util.Log
import com.example.studyintervaltimer.data.ModelsRepository
import com.example.studyintervaltimer.ui.time.timer.TimerUiState
import com.example.studyintervaltimer.ui.time.timer.toTimer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow

interface TickStrategy {
    val onTimerFinish: () -> Unit
    suspend fun tick(
        uiState: StateFlow<TimerUiState>,
        modelsRepository: ModelsRepository,
    )
}

class SingleTimerTickStrategy(
    override val onTimerFinish: () -> Unit,
) : TickStrategy {

    override suspend fun tick(
        uiState: StateFlow<TimerUiState>,
        modelsRepository: ModelsRepository,
    ) {
        if (uiState.value.timerDetails.remainingTime.getAsMs() <= 0) {
            coroutineScope {
                modelsRepository.updateTimer(
                    uiState.value.copy(
                        timerDetails =
                        uiState.value.timerDetails.copy(
                            isTimerRunning = false
                        )
                    ).toTimer()
                )
                onTimerFinish()
            }
        } else if (uiState.value.timerDetails.isTimerRunning) {
            delay(1000L)
            coroutineScope {
                modelsRepository.updateTimer(
                    uiState.value.copy(
                        timerDetails =
                        uiState.value.timerDetails.copy(
                            remainingTimeMs = uiState.value.timerDetails.remainingTime.getAsMs()
                                .minus(1000L),
                            elapsedTime = uiState.value.timerDetails.elapsedTime + 1000L
                        )
                    ).toTimer()
                )
            }
        }
    }
}

class ChainedTimerTickStrategy(
    override val onTimerFinish: () -> Unit,
    private val currentTimerId: Long,
) : TickStrategy {
    private val singleTimerTickStrategy =
        SingleTimerTickStrategy(onTimerFinish = onTimerFinish)

    override suspend fun tick(
        uiState: StateFlow<TimerUiState>,
        modelsRepository: ModelsRepository,
    ) {
        if (currentTimerId == uiState.value.timerDetails.id) {
            singleTimerTickStrategy.tick(uiState = uiState, modelsRepository = modelsRepository)
        }
    }
}
