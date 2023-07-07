package com.example.studyintervaltimer.ui.home

import android.annotation.SuppressLint
import androidx.annotation.StringRes
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.studyintervaltimer.R
import com.example.studyintervaltimer.StudyIntervalTimerTopAppBar
import com.example.studyintervaltimer.ui.navigation.NavigationDestination
import com.example.studyintervaltimer.data.IntervalsDataSource

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToWorkout: (Int) -> Unit,
) {
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
                .fillMaxWidth()
        )
        {
            WorkoutCard(
                title = IntervalsDataSource.classicPomodoro.title,
                navigateToWorkout = navigateToWorkout,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutCard(@StringRes title: Int, navigateToWorkout: (Int) -> Unit, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        onClick = {
            navigateToWorkout(title)
        }
    ) {
        Column(modifier = Modifier.padding(48.dp, 32.dp)) {
            Text(
                text = stringResource(R.string.classic_pomodoro),
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
