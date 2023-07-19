package com.niqr.tasks.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.niqr.core.ui.utils.daggerViewModel
import com.niqr.tasks.ui.di.TasksUiComponentProvider

const val TasksScreenRoutePattern = "tasks"

fun NavController.navigateToTasks() {
    this.navigate((TasksScreenRoutePattern)) {
        popUpTo(0)
        launchSingleTop = true
    }
}

/**
 * Tasks navigation graph
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.tasksScreen(
    onNavigateToCreateTask: () -> Unit,
    onNavigateToEditTask: (String) -> Unit,
    onSignOut: () -> Unit
) {
    composable(TasksScreenRoutePattern) {
        val context = LocalContext.current
        val viewModel = daggerViewModel {
            (context.applicationContext as TasksUiComponentProvider)
                .provideTasksUiComponent().getViewModel()
        }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        TasksScreen(
            uiState = uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onCreateTask = onNavigateToCreateTask,
            onEditTask = onNavigateToEditTask,
            onSignOut = onSignOut
        )
    }
}
