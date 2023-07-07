package com.example.studyintervaltimer.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val modelsRepository: ModelsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineModelsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementatio for [ModelsRepository]
     */
    override val modelsRepository by lazy {
        OfflineModelsRepository(StudyIntervalTimerDatabase.getDatabase(context).modelsDao())
    }
}