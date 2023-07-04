package com.niqr.tasks.ui.model

sealed class TasksEvent {
    object ConnectionError: TasksEvent()
    data class NavigateToEditTask(val id: String): TasksEvent()
    object NavigateToNewTask: TasksEvent()
    object SignOut: TasksEvent()
}