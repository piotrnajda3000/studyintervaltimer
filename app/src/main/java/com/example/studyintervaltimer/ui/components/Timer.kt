package com.example.studyintervaltimer.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun Timer(
    chainedTimer: ChainedTimer,
    currentTimerNo: Int,
    modifier: Modifier = Modifier,
) {
    val timerUiState by chainedTimer.getTimerUiState().collectAsState()

    val progress = (chainedTimer.timerViewModel.elapsedTime.toDouble() / timerUiState.totalTime.getAsMs().toDouble()).toFloat()
    
    LaunchedEffect(
        key1 = timerUiState.remainingTime.getAsMs(),
        key2 = timerUiState.isTimerRunning,
        key3 = currentTimerNo
    ) {
        chainedTimer.tick(currentTimerNo)
    }


    val text = if (chainedTimer.timerInstanceId.equals(currentTimerNo)) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    val background = if (chainedTimer.timerInstanceId.equals(currentTimerNo)) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.background
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().background(background).padding(PaddingValues(vertical = 16.dp))
    ) {
        Text(text = "Timer ${chainedTimer.timerInstanceId}", color = text)
        Text(
            text = timerUiState.remainingTime.displayAsMinutes(),
            style = MaterialTheme.typography.displayLarge,
            color = text
        )
        LinearProgressIndicator(progress =  progress,
        trackColor = MaterialTheme.colorScheme.background,
        color = MaterialTheme.colorScheme.primary)
    }

}