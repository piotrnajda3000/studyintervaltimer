package com.example.studyintervaltimer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyintervaltimer.data.ModelsRepository
import com.example.studyintervaltimer.data.TimersSetWithTimers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


/**
 * View Model to retrieve all items in the Room database.
 */
class HomeViewModel(modelsRepository: ModelsRepository) : ViewModel() {
    /**
     * Holds home ui state. The list of items are retrieved from [ModelsRepository] and mapped to
     * [HomeUiState]
     */
    val homeUiState: StateFlow<HomeUiState> =
        modelsRepository.getAllTimersSetsWithTimersStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val timerSetsWithTimers: List<TimersSetWithTimers> = listOf())
