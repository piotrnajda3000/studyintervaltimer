package com.example.studyintervaltimer.ui.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.studyintervaltimer.AppViewModelProvider
import com.example.studyintervaltimer.R
import com.example.studyintervaltimer.StudyIntervalTimerTopAppBar
import com.example.studyintervaltimer.ui.navigation.NavigationDestination
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyintervaltimer.ui.time.displayAsMinutes

object ProgressDestination : NavigationDestination {
    override val route = "progress"
    override val titleRes = R.string.progress
}

@Composable
fun ProgressScreen(navigateToHome: () -> Unit, viewModel: ProgressViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val progressUiState by viewModel.progressUiState.collectAsState()
    Scaffold(
        topBar = {
            StudyIntervalTimerTopAppBar(
                title = stringResource(id = ProgressDestination.titleRes),
                canNavigateBack = false
            )
        },
        bottomBar = {
            BottomAppBar() {
                Row() {
                    IconButton(
                        onClick = {
                            navigateToHome()
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.workout),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = {
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = stringResource(R.string.progress),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 8.dp,
                    end = 8.dp
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier
                .fillMaxWidth()
                ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
// Work / break type timers  will be implemented later
//                    Column {
//                        Text(text = "Work time", style = MaterialTheme.typography.labelLarge)
//                        Text(text = "5 hrs", style = MaterialTheme.typography.bodySmall)
//                    }
//                    Column {
//                        Text(text = "Break time", style = MaterialTheme.typography.labelLarge)
//                        Text(text = "5 hrs", style = MaterialTheme.typography.bodySmall)
//                    }
                    Column {
                        Text(text = "Total", style = MaterialTheme.typography.labelLarge)
                        Text(text = progressUiState.timeDetails.toTime().displayAsMinutes(), style = MaterialTheme.typography.bodySmall)
                    }
                }

            }
        }

    }
}