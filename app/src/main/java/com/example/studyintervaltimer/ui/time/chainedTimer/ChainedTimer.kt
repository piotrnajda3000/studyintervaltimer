package com.example.studyintervaltimer.ui.time.chainedTimer

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
import com.example.studyintervaltimer.ui.time.timer.TimerViewModel
import com.example.studyintervaltimer.ui.time.TickStrategy
import com.example.studyintervaltimer.ui.time.displayAsMinutes
import com.example.studyintervaltimer.ui.time.getAsMs
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChainedTimer(
    timer: TimerViewModel,
    currentTimerNo: Long,
    modifier: Modifier = Modifier,
    tickStrategy: TickStrategy,
) {
    val timerUiState by timer.timerUiState.collectAsState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    var height = 0f;

    LaunchedEffect(
        key1 = timerUiState.timerDetails.remainingTime.getAsMs(),
        key2 = timerUiState.timerDetails.isTimerRunning,
        key3 = currentTimerNo
    ) {
        timer.tick(tickStrategy)

        // Scroll the current timer into view
        if (timerUiState.timerDetails.id == currentTimerNo) {
            coroutineScope.launch {
                bringIntoViewRequester.bringIntoView(
                    rect = Rect(0f, -100f, 0f, height + 96f)
                )
            }
        }
    }

    val text = if (timerUiState.timerDetails.id == currentTimerNo) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    val background = if (timerUiState.timerDetails.id == currentTimerNo) {
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
        Text(text = "Timer ${timerUiState.timerDetails.id}", color = text)
        Text(
            text = timerUiState.timerDetails.remainingTime.displayAsMinutes(),
            style = MaterialTheme.typography.displayLarge,
            color = text
        )
        LinearProgressIndicator(
            progress = timer.getProgress(),
            trackColor = MaterialTheme.colorScheme.background,
            color = MaterialTheme.colorScheme.primary
        )
    }

}