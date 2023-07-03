package com.niqr.todoapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.todoapp.data.abstraction.AuthInfoProvider
import com.niqr.todoapp.ui.auth.AuthScreenRoutePattern
import com.niqr.todoapp.ui.auth.authScreen
import com.niqr.todoapp.ui.auth.navigateToAuth
import com.niqr.todoapp.ui.edit.navigateToTaskEdit
import com.niqr.todoapp.ui.edit.taskEditScreen
import com.niqr.todoapp.ui.tasks.TasksScreenRoutePattern
import com.niqr.todoapp.ui.tasks.navigateToTasks
import com.niqr.todoapp.ui.tasks.tasksScreen

@Composable
fun TodoNavigation(authProvider: AuthInfoProvider) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ExtendedTheme.colors.backPrimary
    ) {
        NavHost(
            navController = navController,
            startDestination = if (authProvider.authInfo().token.isBlank()) AuthScreenRoutePattern else TasksScreenRoutePattern
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