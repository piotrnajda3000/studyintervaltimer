package com.example.studyintervaltimer.data

import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface ModelsRepository {
    fun getAllTimersSetsWithTimersStream(): Flow<List<TimersSetWithTimers>>
    fun getTimerSetWithTimersStream(id: Long): Flow<TimersSetWithTimers>

    suspend fun insertTimer(timer: Timer): Long

    suspend fun insertTimersSet(timerSet: TimersSet): Long

    suspend fun updateTimersSet(timersSet: TimersSet)

    suspend fun updateTimer(timer: Timer)
}