package com.niqr.todoapp.ui.tasks.model

sealed class TasksUiEvent {
    data class NavigateToEditTask(val id: String): TasksUiEvent()
    object NavigateToNewTask: TasksUiEvent()
}