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
import com.example.studyintervaltimer.ui.progress.ProgressDestination
import com.example.studyintervaltimer.ui.progress.ProgressScreen

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
                },
                navigateToProgress = {
                    navController.navigate(ProgressDestination.route)
                }
            )
        }
        composable(
            route = ProgressDestination.route
        ) {
            ProgressScreen(
                navigateToHome = {
                    navController.navigate(HomeDestination.route)
                },
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
