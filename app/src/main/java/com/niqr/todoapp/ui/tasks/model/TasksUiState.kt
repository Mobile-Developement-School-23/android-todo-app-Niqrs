package com.niqr.todoapp.ui.tasks.model

import com.niqr.todoapp.data.model.TodoItem

data class TasksUiState(
    val doneVisible: Boolean = true,
    val tasks: List<TodoItem> = emptyList()
)