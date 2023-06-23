package com.niqr.todoapp.ui.tasks.model

import com.niqr.todoapp.data.model.TodoItem

sealed class TasksUiAction {
    data class UpdateTask(val todoItem: TodoItem): TasksUiAction()
    data class DeleteTask(val id: String) : TasksUiAction()
    data class EditTask(val todoItem: TodoItem) : TasksUiAction()
    data class UpdateDoneVisibility(val visible: Boolean): TasksUiAction()
}