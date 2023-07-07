package com.example.studyintervaltimer.ui.home

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studyintervaltimer.R
import com.example.studyintervaltimer.StudyIntervalTimerTopAppBar
import com.example.studyintervaltimer.ui.navigation.NavigationDestination
import com.example.studyintervaltimer.data.IntervalsDataSource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyintervaltimer.AppViewModelProvider
import com.example.studyintervaltimer.data.TimersSetWithTimers

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToWorkout: (Long) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    Scaffold(
        topBar = {
            StudyIntervalTimerTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        )
        {
            homeUiState.timerSetsWithTimers.forEach { set ->
                WorkoutCard(
                    workout = set,
                    navigateToWorkout = navigateToWorkout,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp).fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutCard(
    workout: TimersSetWithTimers,
    navigateToWorkout: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        onClick = {
            navigateToWorkout(workout.timerSet.timerSetId)
        }
    ) {
        Column(modifier = Modifier.padding(48.dp, 32.dp)) {
            Text(
                text = workout.timerSet.name,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}
