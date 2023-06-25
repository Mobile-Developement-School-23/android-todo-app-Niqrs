package com.niqr.todoapp.ui.tasks

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val TasksScreenRoutePattern = "tasks"

internal fun NavController.navigateToTasks() {
    this.navigate((TasksScreenRoutePattern)) {
        popUpTo(0)
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.tasksScreen(
    onNavigateToCreateTask: () -> Unit,
    onNavigateToEditTask: (String) -> Unit
) {
    composable(TasksScreenRoutePattern) {
        val viewModel: TasksViewModel = hiltViewModel()
        TasksScreen(
            uiState = viewModel.uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onCreateTask = onNavigateToCreateTask,
            onEditTask = onNavigateToEditTask
        )
    }
}