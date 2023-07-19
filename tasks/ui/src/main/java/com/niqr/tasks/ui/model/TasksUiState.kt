package com.niqr.tasks.ui.model

import com.niqr.settings.domain.model.Theme
import com.niqr.tasks.domain.model.TodoItem
import com.niqr.tasks.ui.TasksScreen

/**
 * Main state of [TasksScreen]
 */
data class TasksUiState(
    val isRefreshing: Boolean = false,
    val doneVisible: Boolean = true,
    val selectedTheme: Theme = Theme.SYSTEM,
    val tasks: List<TodoItem> = emptyList()
)
