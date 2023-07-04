package com.niqr.edit.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

internal const val TaskId = "taskId"
private const val TaskEdit = "taskEdit"
const val TaskEditScreenRoutePattern = "$TaskEdit?$TaskId={$TaskId}"

fun NavController.navigateToTaskEdit(id: String = "") {
    this.navigate(
        route = if (id.isNotBlank()) "$TaskEdit?$TaskId=$id"
            else TaskEdit
    ) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.taskEditScreen(
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