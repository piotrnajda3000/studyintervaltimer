package com.example.studyintervaltimer

import android.app.Application
import com.example.studyintervaltimer.data.AppContainer
import com.example.studyintervaltimer.data.AppDataContainer

class StudyIntervalTimerApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}

