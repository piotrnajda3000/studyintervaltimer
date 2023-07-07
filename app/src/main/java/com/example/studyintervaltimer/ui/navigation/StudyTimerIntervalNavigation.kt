package com.example.studyintervaltimer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.studyintervaltimer.ui.time.chainedTimer.ChainedTimersDestination
import com.example.studyintervaltimer.ui.time.chainedTimer.ChainedTimersScreen
import com.example.studyintervaltimer.ui.home.HomeDestination
import com.example.studyintervaltimer.ui.home.HomeScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun StudyIntervalTimerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToWorkout = { id ->
                    navController.navigate("${ChainedTimersDestination.route}/${id}")
                }
            )
        }
        composable(
            route = ChainedTimersDestination.routeWithArgs, arguments = listOf(
                navArgument(ChainedTimersDestination.workoutId) {
                    type = NavType.LongType
                })
        ) {
            ChainedTimersScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
            )
        }
    }
}
