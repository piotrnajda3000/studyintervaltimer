package com.example.studyintervaltimer.ui.components

class TimerUiState(
    private var totalTimeMs: Long = 0,
    private var remainingTimeMs: Long = totalTimeMs,
    var isTimerRunning: Boolean = false,
) {
    var totalTime = Time(totalTimeMs)
    var remainingTime = Time(remainingTimeMs)

    fun copy(
        isTimerRunning: Boolean = this.isTimerRunning,
        remainingTimeMs: Long = this.remainingTimeMs,
    ): TimerUiState {
        return TimerUiState(
            totalTimeMs = this.totalTimeMs,
            remainingTimeMs = remainingTimeMs,
            isTimerRunning = isTimerRunning,
        )
    }
}
