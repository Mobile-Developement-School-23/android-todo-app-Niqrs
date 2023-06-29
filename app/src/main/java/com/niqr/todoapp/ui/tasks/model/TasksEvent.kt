package com.niqr.todoapp.ui.tasks.model

sealed class TasksEvent {
    data class NavigateToEditTask(val id: String): TasksEvent()
    object NavigateToNewTask: TasksEvent()
    object SignOut: TasksEvent()
}