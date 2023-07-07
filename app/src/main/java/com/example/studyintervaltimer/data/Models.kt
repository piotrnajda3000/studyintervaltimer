package com.example.studyintervaltimer.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class TimersSet(
    @PrimaryKey
    val timerSetId: Long = 0,
    val name: String = "Interval $timerSetId",
    val currentTimerId: Long = 0,
)

@Entity
data class Timer(
    @PrimaryKey
    val timerId: Long = 0,
    val isInTimerSetId: Long,
    val totalTimeMs: Long = 0,
    val isTimerRunning: Boolean = false,
    val remainingTimeMs: Long = totalTimeMs,
    val elapsedTime: Long = 0,
)

data class TimersSetWithTimers(
    @Embedded val timerSet: TimersSet,
    @Relation(
        parentColumn = "timerSetId",
        entityColumn = "isInTimerSetId"
    )
    val timers: List<Timer>,
)

