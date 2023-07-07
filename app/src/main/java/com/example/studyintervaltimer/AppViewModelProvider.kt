package com.example.studyintervaltimer

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studyintervaltimer.ui.home.HomeViewModel
import com.example.studyintervaltimer.ui.time.chainedTimer.ChainedTimersViewModel
import com.example.studyintervaltimer.ui.time.timer.TimerViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                studyIntervalTimerApplication().container.modelsRepository
            )
        }
        initializer {
            ChainedTimersViewModel(
                this.createSavedStateHandle(),
                studyIntervalTimerApplication().container.modelsRepository
            )
        }

//        initializer {
//            TimerViewModel(
//                studyIntervalTimerApplication().container.modelsRepository
//            )
//        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [StudyIntervalTimerApplication].
 */
fun CreationExtras.studyIntervalTimerApplication(): StudyIntervalTimerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as StudyIntervalTimerApplication)
