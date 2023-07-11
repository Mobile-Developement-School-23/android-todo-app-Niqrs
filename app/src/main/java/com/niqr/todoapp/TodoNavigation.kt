package com.niqr.todoapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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
@Composable
fun TodoNavigation(authProvider: AuthInfoProvider) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ExtendedTheme.colors.backPrimary
    ) {
        NavHost(
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
