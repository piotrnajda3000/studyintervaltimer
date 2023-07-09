package com.example.studyintervaltimer.data

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Time::class)
    fun insertTime(time: Time)

    @Query("UPDATE Time SET time = time + :timeMs WHERE timeId = :id")
    fun incTimeBy(id: Long, timeMs: Long)

    @Query("SELECT * from Time WHERE timeId = :id")
    fun getTime(id: Long): Time?

    @Query("SELECT * from Time WHERE timeId = :id")
    fun getTimeStream(id: Long): Flow<Time>

    fun incOrInsertTime(time: Time) {
        val test = getTime(time.timeId)
        if (test == null) {
            insertTime(time)
        } else {
            incTimeBy(time.timeId, time.time)
        }
    }
}