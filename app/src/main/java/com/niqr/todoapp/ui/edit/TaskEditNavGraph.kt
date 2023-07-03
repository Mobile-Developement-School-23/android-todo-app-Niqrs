package com.niqr.todoapp.ui.edit

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

internal const val TaskId = "taskId"
private const val TaskEdit = "taskEdit"
internal const val TaskEditScreenRoutePattern = "$TaskEdit?$TaskId={$TaskId}"

internal fun NavController.navigateToTaskEdit(id: String = "") {
    this.navigate(
        route = if (id.isNotBlank()) "$TaskEdit?$TaskId=$id"
            else TaskEdit
    ) {
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.taskEditScreen(
    onNavigateUp: () -> Unit,
    onSuccessSave: () -> Unit
) {
    composable(
        route = TaskEditScreenRoutePattern,
        arguments = listOf(navArgument(TaskEdit) {
            nullable = true
        }
        )
    ) {
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