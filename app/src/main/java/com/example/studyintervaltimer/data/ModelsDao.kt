package com.example.studyintervaltimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.studyintervaltimer.ui.time.chainedTimer.ChainedTimersSetDetails
import kotlinx.coroutines.flow.Flow


@Dao
interface ModelsDao {
    @Transaction
    @Query("SELECT * from TimersSet")
    fun getTimerSetsWithTimers(): Flow<List<TimersSetWithTimers>>

    @Query("SELECT * from Timer WHERE timerId = :id")
    fun getTimer(id: Long): Flow<Timer>

    @Transaction
    @Query("SELECT * from TimersSet WHERE timerSetId = :id")
    fun getTimerSetWithTimers(id: Long): Flow<TimersSetWithTimers>

    @Insert
    fun insertTimer(timer: Timer): Long

    @Update
    fun updateTimersSet(timersSet: TimersSet)

    @Update
    fun updateTimer(timer: Timer)

    @Insert
    fun insertTimersSet(timersSet: TimersSet): Long
}