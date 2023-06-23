package com.niqr.todoapp.compose.ui.tasks.model

sealed class TasksEvent {
    data class NavigateToEditTask(val id: String): TasksEvent()
    object NavigateToNewTask: TasksEvent()
}