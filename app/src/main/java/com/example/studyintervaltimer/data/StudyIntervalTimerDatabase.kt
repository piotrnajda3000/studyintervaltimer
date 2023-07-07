package com.example.studyintervaltimer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(
    entities = [TimersSet::class,Timer::class],
    version = 20,
    exportSchema = false
)
abstract class StudyIntervalTimerDatabase : RoomDatabase() {
    abstract fun modelsDao(): ModelsDao
    companion object {
        @Volatile
        private var Instance: StudyIntervalTimerDatabase? = null
        fun getDatabase(context: Context): StudyIntervalTimerDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    StudyIntervalTimerDatabase::class.java,
                    "study_interval_timer_database"
                )
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
