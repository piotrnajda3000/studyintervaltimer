package com.example.studyintervaltimer.ui.time.timer

import com.example.studyintervaltimer.data.Timer
import com.example.studyintervaltimer.ui.time.Time

class TimerDetails(
    val id: Long = 0,
    val totalTimeMs: Long = 0,
    val isTimerRunning: Boolean = false,
    val remainingTimeMs: Long = totalTimeMs,
    val elapsedTime: Long = 0,
    val timerSetId: Long = 0,
) {
    val totalTime = Time(totalTimeMs)
    val remainingTime = Time(remainingTimeMs)
    fun copy(
        id: Long = this.id,
        timerSetId: Long = this.timerSetId,
        totalTimeMs: Long = this.totalTimeMs,
        remainingTimeMs: Long = this.remainingTimeMs,
        isTimerRunning: Boolean = this.isTimerRunning,
        elapsedTime: Long = this.elapsedTime,
    ): TimerDetails {
        return TimerDetails(
            id = id,
            timerSetId = timerSetId,
            totalTimeMs = totalTimeMs,
            remainingTimeMs = remainingTimeMs,
            isTimerRunning = isTimerRunning,
            elapsedTime = elapsedTime,
        )
    }
}

data class TimerUiState(
    val timerDetails: TimerDetails = TimerDetails(),
)

fun TimerUiState.toTimer(timerSetId: Long = this.timerDetails.timerSetId): Timer {
    return Timer(
        timerId = this.timerDetails.id,
        isTimerRunning = this.timerDetails.isTimerRunning,
        elapsedTime = this.timerDetails.elapsedTime,
        totalTimeMs = this.timerDetails.totalTimeMs,
        remainingTimeMs = this.timerDetails.remainingTimeMs,
        isInTimerSetId = timerSetId
    )
}
