package com.example.studyintervaltimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.studyintervaltimer.data.IntervalsDataSource
import com.example.studyintervaltimer.data.ModelsDao
import com.example.studyintervaltimer.data.StudyIntervalTimerDatabase
import com.example.studyintervaltimer.ui.theme.StudyIntervalTimerTheme
import com.example.studyintervaltimer.ui.time.timer.toTimer

class MainActivity : ComponentActivity() {
    lateinit var db: StudyIntervalTimerDatabase
    lateinit var dao: ModelsDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set to false to not populate DB.
        if (false) {
            db = StudyIntervalTimerDatabase.getDatabase(context = this)
            dao = db.modelsDao()
            populateDb()
        }

        setContent {
            StudyIntervalTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudyIntervalTimerApp()
                }
            }
        }
    }
    private fun populateDb() {
        val timersSetId = dao.insertTimersSet(IntervalsDataSource.classicPomodoro.timersSet)
        IntervalsDataSource.classicPomodoro.timers.forEach { timer ->
            dao.insertTimer(timer.toTimer(timersSetId))
        }
    }
}

