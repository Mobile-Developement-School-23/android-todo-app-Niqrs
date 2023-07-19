package com.niqr.todoapp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.niqr.auth.domain.AuthInfoProvider
import com.niqr.auth.ui.AuthScreenRoutePattern
import com.niqr.auth.ui.authScreen
import com.niqr.auth.ui.navigateToAuth
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.edit.ui.navigateToTaskEdit
import com.niqr.edit.ui.taskEditScreen
import com.niqr.tasks.ui.TasksScreenRoutePattern
import com.niqr.tasks.ui.navigateToTasks
import com.niqr.tasks.ui.tasksScreen

/**
 * Root of app navigation graph
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TodoNavigation(authProvider: AuthInfoProvider) {
    val navController = rememberAnimatedNavController()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ExtendedTheme.colors.backPrimary
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = if (authProvider.authInfo().token.isBlank()) AuthScreenRoutePattern
                else TasksScreenRoutePattern
        ) {
            authScreen(
                onSuccessAuth = navController::navigateToTasks
            )
            tasksScreen(
                onNavigateToCreateTask = navController::navigateToTaskEdit,
                onNavigateToEditTask = navController::navigateToTaskEdit,
                onSignOut = navController::navigateToAuth
            )
            taskEditScreen(
                onNavigateUp = navController::navigateUp,
                onSuccessSave = navController::navigateUp
            )
        }
    }
}
