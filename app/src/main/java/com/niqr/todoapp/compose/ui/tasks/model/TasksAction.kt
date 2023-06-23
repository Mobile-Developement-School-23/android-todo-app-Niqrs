package com.niqr.todoapp.compose.ui.tasks.model

import com.niqr.todoapp.data.model.TodoItem

sealed class TasksAction {
    data class UpdateTask(val todoItem: TodoItem): TasksAction()
    data class DeleteTask(val id: String) : TasksAction()
    data class EditTask(val todoItem: TodoItem) : TasksAction()
    data class UpdateDoneVisibility(val visible: Boolean): TasksAction()
}