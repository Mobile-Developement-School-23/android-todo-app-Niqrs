package com.niqr.todoapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.niqr.todoapp.compose.ui.taskEdit.navigateToTaskEdit
import com.niqr.todoapp.compose.ui.taskEdit.taskEditScreen
import com.niqr.todoapp.compose.ui.tasks.TasksScreenRoutePattern
import com.niqr.todoapp.compose.ui.tasks.tasksScreen
import com.niqr.todoapp.compose.ui.theme.ExtendedTheme

@Composable
fun TodoNavigation() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ExtendedTheme.colors.backPrimary
    ) {
        NavHost(
            navController = navController,
            startDestination = TasksScreenRoutePattern
        ) {
            tasksScreen(
                onNavigateToCreateTask = navController::navigateToTaskEdit,
                onNavigateToEditTask = { TODO() }
            )
            taskEditScreen(
                onNavigateUp = navController::navigateUp,
                onSuccessSave = navController::navigateUp
            )
        }
    }
}