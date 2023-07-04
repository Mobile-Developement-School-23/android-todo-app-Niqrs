package com.niqr.tasks.ui.model

import com.niqr.tasks.domain.model.TodoItem

data class TasksUiState(
    val isRefreshing: Boolean = false,
    val doneVisible: Boolean = true,
    val tasks: List<TodoItem> = emptyList()
)
