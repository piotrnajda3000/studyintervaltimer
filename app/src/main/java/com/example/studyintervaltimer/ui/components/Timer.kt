package com.example.studyintervaltimer.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Timer(
    chainedTimer: ChainedTimer,
    currentTimerNo: Int,
    modifier: Modifier = Modifier,
) {
    val timerUiState by chainedTimer.getTimerUiState().collectAsState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    var height = 0f;

    LaunchedEffect(
        key1 = timerUiState.remainingTime.getAsMs(),
        key2 = timerUiState.isTimerRunning,
        key3 = currentTimerNo
    ) {
        chainedTimer.tick(currentTimerNo)
        // Scroll the current timer into view
        if (chainedTimer.timerInstanceId.equals(currentTimerNo)) {
            coroutineScope.launch {
                bringIntoViewRequester.bringIntoView(
                    rect = Rect(0f, -100f, 0f, height + 96f))
            }
        }
    }

    val text = if (chainedTimer.timerInstanceId == currentTimerNo) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    val background = if (chainedTimer.timerInstanceId == currentTimerNo) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.background
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(background)
            .padding(PaddingValues(vertical = 16.dp))
            .bringIntoViewRequester(bringIntoViewRequester)
            .onSizeChanged {
                height = it.height.toFloat()
            }
    )
    {
        Text(text = "Timer ${chainedTimer.timerInstanceId}", color = text)
        Text(
            text = timerUiState.remainingTime.displayAsMinutes(),
            style = MaterialTheme.typography.displayLarge,
            color = text
        )
        LinearProgressIndicator(
            progress = chainedTimer.timerViewModel.getProgress(),
            trackColor = MaterialTheme.colorScheme.background,
            color = MaterialTheme.colorScheme.primary
        )
    }

}