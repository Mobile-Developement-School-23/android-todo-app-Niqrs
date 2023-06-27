package com.niqr.todoapp.ui.tasks.model

import com.niqr.todoapp.data.model.TodoItem

sealed class TasksAction {
    object CreateTask: TasksAction()
    data class UpdateTask(val todoItem: TodoItem): TasksAction()
    data class DeleteTask(val todoItem: TodoItem) : TasksAction()
    data class EditTask(val todoItem: TodoItem) : TasksAction()
    data class UpdateDoneVisibility(val visible: Boolean): TasksAction()
}