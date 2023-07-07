package com.niqr.edit.ui

import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.niqr.core.ui.utils.daggerViewModel
import com.niqr.edit.ui.di.EditUiComponentProvider

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

/**
 * Edit navigation graph
 */
fun NavGraphBuilder.taskEditScreen(
    onNavigateUp: () -> Unit,
    onSuccessSave: () -> Unit
) {
    composable(
        route = TaskEditScreenRoutePattern,
        arguments = listOf(navArgument(TaskEdit) {
            nullable = true
        })
    ) {
        val context = LocalContext.current
        val viewModel: TaskEditViewModel = daggerViewModel {
            (context.applicationContext as EditUiComponentProvider)
                .provideEditUiComponent().getViewModel()
                .apply { setupViewModel(it.arguments) }
        }

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        TaskEditScreen(
            uiState = uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onNavigateUp = onNavigateUp,
            onSave = onSuccessSave
        )
    }
}
