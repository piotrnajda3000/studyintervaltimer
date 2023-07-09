package com.example.studyintervaltimer.data

import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface ModelsRepository {
    fun getTimerStream(id: Long): Flow<Timer>
    fun getTimerSetWithTimersStream(id: Long): Flow<TimersSetWithTimers>
    fun getAllTimersSetsWithTimersStream(): Flow<List<TimersSetWithTimers>>

    suspend fun insertTimer(timer: Timer): Long
    suspend fun updateTimer(timer: Timer)

    suspend fun insertTimersSet(timerSet: TimersSet): Long
    suspend fun updateTimersSet(timersSet: TimersSet)
    
    suspend fun insertTime(time: Time)
    suspend fun incTimeBy(time: Time)
    fun getTimeStream(id: Long): Flow<Time>
}