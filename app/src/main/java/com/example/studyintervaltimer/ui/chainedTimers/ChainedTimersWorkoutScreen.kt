package com.example.studyintervaltimer.ui.chainedTimers

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.studyintervaltimer.R
import com.example.studyintervaltimer.StudyIntervalTimerTopAppBar
import com.example.studyintervaltimer.data.IntervalsDataSource
import com.example.studyintervaltimer.ui.components.ChainedTimersViewModel
import com.example.studyintervaltimer.ui.components.Timer
import com.example.studyintervaltimer.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ChainedTimersWorkoutDestination : NavigationDestination {
    override val route = "chained_timers_workout"
    override val titleRes = R.string.workout
    const val workoutId = "workoutId"
    val routeWithArgs = "$route/{$workoutId}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChainedTimersWorkoutScreen(
    chainedTimersViewModel: ChainedTimersViewModel,
    onNavigateUp: () -> Unit,
    @StringRes title: Int,
) {
    val timersUiState by chainedTimersViewModel.timersUiState.collectAsState()
    if (!timersUiState.haveTimersBeenInitialized) {
        LaunchedEffect(Unit) {
            IntervalsDataSource.classicPomodoro.timers.forEach { interval ->
                chainedTimersViewModel.addTimer(interval)
            }
            chainedTimersViewModel.setHaveTimersBeenInitialized(true);
        }
    }
    Scaffold(
        topBar = {
            StudyIntervalTimerTopAppBar(
                title = stringResource(id = title),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        bottomBar = {
            BottomAppBar(actions = {
                Row() {
                    IconButton(
                        onClick = {
                                chainedTimersViewModel.prevTimer();
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.prevTimer),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    FilledTonalIconButton(
                        onClick = {
                                chainedTimersViewModel.pauseOrResume()
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = stringResource(R.string.resume),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(
                        onClick = {
                                chainedTimersViewModel.nextTimer();
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = stringResource(R.string.nextTimer),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            })
        }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .zIndex(1f),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                timersUiState.timers.forEach { timer ->
                    Timer(
                        chainedTimer = timer,
                        currentTimerNo = timersUiState.currentTimerNo,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}