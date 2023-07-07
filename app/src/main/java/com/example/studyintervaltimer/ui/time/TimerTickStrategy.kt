package com.example.studyintervaltimer.ui.time

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyintervaltimer.data.ModelsRepository
import com.example.studyintervaltimer.ui.time.timer.TimerUiState
import com.example.studyintervaltimer.ui.time.timer.toTimer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.coroutineContext

interface TickStrategy {
    fun onTimerFinish(): () -> Unit = { }
    suspend fun tick(
        uiState: StateFlow<TimerUiState>,
        modelsRepository: ModelsRepository,
    )
}

class SingleTimerTickStrategy(
    private val onTimerFinish: () -> Unit = { },
) : TickStrategy {
    override fun onTimerFinish(): () -> Unit = onTimerFinish

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
            }
//            uiState.update {
//                it.copy(timerDetails = it.timerDetails.copy(isTimerRunning = false))
//            }
            onTimerFinish()
        }
        if (uiState.value.timerDetails.isTimerRunning) {
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
//            uiState.update {
//                it.copy(
//                    timerDetails = it.timerDetails.copy(
//                        remainingTimeMs = it.timerDetails.remainingTime.getAsMs().minus(1000L),
//                        elapsedTime = it.timerDetails.elapsedTime + 1000L
//                    )
//                )
//            }
        }
    }
}

class ChainedTimerTickStrategy(
    onTimerFinish: () -> Unit,
    private val currentTimerId: Long,
) : TickStrategy {
    private val singleTimerTickStrategy = SingleTimerTickStrategy(onTimerFinish)
    override suspend fun tick(
        uiState: StateFlow<TimerUiState>,
        modelsRepository: ModelsRepository,
    ) {
        if (currentTimerId == uiState.value.timerDetails.id) {
            singleTimerTickStrategy.tick(uiState = uiState, modelsRepository = modelsRepository)
        }
    }
}
