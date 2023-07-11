package com.niqr.tasks.ui.model

import com.niqr.tasks.domain.model.TodoItem
import com.niqr.tasks.ui.TasksScreen

/**
 * Main state of [TasksScreen]
 */
data class TasksUiState(
    val isRefreshing: Boolean = false,
    val doneVisible: Boolean = true,
    val tasks: List<TodoItem> = emptyList()
)
