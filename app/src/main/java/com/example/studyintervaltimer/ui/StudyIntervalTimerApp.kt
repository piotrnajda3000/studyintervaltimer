package com.example.studyintervaltimer.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.studyintervaltimer.ui.components.ChainedTimersViewModel
import com.example.studyintervaltimer.ui.components.Timer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyintervaltimer.R
import com.example.studyintervaltimer.data.IntervalsDataSource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyIntervalTimerApp(
    chainedTimersViewModel: ChainedTimersViewModel = viewModel(),
) {
    val timersUiState by chainedTimersViewModel.timersUiState.collectAsState()

    LaunchedEffect(Unit) {
        IntervalsDataSource.testing.forEach { interval ->
            chainedTimersViewModel.addTimer(interval)
        }
    }

    Scaffold(bottomBar = {
        BottomAppBar(actions = {
            Row() {
                IconButton(
                    onClick = { },
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
//                      chainedTimersViewModel.nextTimer();
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