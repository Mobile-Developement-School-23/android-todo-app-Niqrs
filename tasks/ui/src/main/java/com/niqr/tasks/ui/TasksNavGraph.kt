package com.niqr.tasks.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val TasksScreenRoutePattern = "tasks"

fun NavController.navigateToTasks() {
    this.navigate((TasksScreenRoutePattern)) {
        popUpTo(0)
        launchSingleTop = true
    }
}

fun NavGraphBuilder.tasksScreen(
    onNavigateToCreateTask: () -> Unit,
    onNavigateToEditTask: (String) -> Unit,
    onSignOut: () -> Unit
) {
    composable(TasksScreenRoutePattern) {
        val viewModel: TasksViewModel = hiltViewModel()
        TasksScreen(
            uiState = viewModel.uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onCreateTask = onNavigateToCreateTask,
            onEditTask = onNavigateToEditTask,
            onSignOut = onSignOut
        )
    }
}
