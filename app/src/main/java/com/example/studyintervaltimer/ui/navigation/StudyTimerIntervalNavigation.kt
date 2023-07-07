package com.example.studyintervaltimer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.studyintervaltimer.ui.chainedTimers.ChainedTimersWorkoutDestination
import com.example.studyintervaltimer.ui.chainedTimers.ChainedTimersWorkoutScreen
import com.example.studyintervaltimer.ui.components.ChainedTimersViewModel
import com.example.studyintervaltimer.ui.home.HomeDestination
import com.example.studyintervaltimer.ui.home.HomeScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun StudyIntervalTimerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    chainedTimersViewModel: ChainedTimersViewModel
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToWorkout = { title ->
                    navController.navigate("${ChainedTimersWorkoutDestination.route}/${title}")
                }
            )
        }
        composable(route = ChainedTimersWorkoutDestination.routeWithArgs, arguments = listOf(
            navArgument(ChainedTimersWorkoutDestination.workoutId) {
                type = NavType.IntType
            })) {
            it.arguments?.getInt(ChainedTimersWorkoutDestination.workoutId)?.let { it1 ->
                ChainedTimersWorkoutScreen(onNavigateUp = {
                    navController.navigateUp()
                }, title = it1, chainedTimersViewModel = chainedTimersViewModel)
            }
        }
    }
}
