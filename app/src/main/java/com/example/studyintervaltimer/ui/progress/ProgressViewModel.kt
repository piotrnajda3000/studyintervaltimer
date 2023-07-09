package com.example.studyintervaltimer.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyintervaltimer.data.ModelsRepository
import com.example.studyintervaltimer.ui.time.Time
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgressViewModel(private val modelsRepository: ModelsRepository) : ViewModel() {
    val progressUiState: StateFlow<ProgressUiState> =
        modelsRepository.getTimeStream(TIME_ID)
            .filterNotNull()
            .map {
            ProgressUiState(
                timeDetails = ProgressTimeDetails(
                        time = it.time,
                        id = it.timeId
                    )
                )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ProgressUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val TIME_ID = 0L
    }
}


data class ProgressUiState(val timeDetails: ProgressTimeDetails = ProgressTimeDetails())

data class ProgressTimeDetails(
    val time: Long = 0,
    val id: Long = 0,
)

fun ProgressTimeDetails.toTime(): Time {
    return Time(
        this.time
    )
}