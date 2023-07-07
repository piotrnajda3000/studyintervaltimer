package com.example.studyintervaltimer.data

import kotlinx.coroutines.flow.Flow

class OfflineModelsRepository (private val modelsDao: ModelsDao) : ModelsRepository {
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
}