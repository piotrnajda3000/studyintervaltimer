package com.example.studyintervaltimer.data

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import kotlinx.coroutines.flow.Flow

class OfflineModelsRepository (private val modelsDao: ModelsDao) : ModelsRepository {
    override fun getTimerStream(id: Long): Flow<Timer> {
        return modelsDao.getTimer(id)
    }

    override suspend fun insertTime(time: Time) {
        return modelsDao.insertTime(time)
    }

    override fun getAllTimersSetsWithTimersStream(): Flow<List<TimersSetWithTimers>> {
        return modelsDao.getTimerSetsWithTimers()
    }

    override suspend fun insertTimer(timer: Timer): Long {
        return modelsDao.insertTimer(timer)
    }

    override suspend fun insertTimersSet(timerSet: TimersSet): Long {
        return modelsDao.insertTimersSet(timerSet)
    }

    override fun getTimerSetWithTimersStream(id: Long): Flow<TimersSetWithTimers> {
        return modelsDao.getTimerSetWithTimers(id)
    }

    override suspend fun updateTimer(timer: Timer) {
        return modelsDao.updateTimer(timer)
    }

    override suspend fun updateTimersSet(timersSet: TimersSet) {
        return modelsDao.updateTimersSet(timersSet)
    }

    override fun getTimeStream(id: Long): Flow<Time> {
        return modelsDao.getTimeStream(id)
    }

    override suspend fun incTimeBy(time: Time) {
        modelsDao.incOrInsertTime(time)
    }
}