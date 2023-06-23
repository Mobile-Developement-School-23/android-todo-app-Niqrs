package com.niqr.todoapp.compose.ui.taskEdit

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val TaskEditScreenRoutePattern = "taskEdit"

internal fun NavController.navigateToTaskEdit() {
    this.navigate(TaskEditScreenRoutePattern) {
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.taskEditScreen(
    onNavigateUp: () -> Unit,
    onSuccessSave: () -> Unit
) {
    composable(TaskEditScreenRoutePattern) {
        val viewModel: TaskEditViewModel = hiltViewModel()
        TaskEditScreen(
            uiState = viewModel.uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onNavigateUp = onNavigateUp,
            onSave = onSuccessSave
        )
    }
}