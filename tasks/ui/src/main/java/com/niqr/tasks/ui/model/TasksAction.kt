package com.niqr.tasks.ui.model

import com.niqr.tasks.domain.model.TodoItem

sealed class TasksAction {
    object CreateTask: TasksAction()
    data class UpdateTask(val todoItem: TodoItem): TasksAction()
    data class DeleteTask(val todoItem: TodoItem) : TasksAction()
    data class EditTask(val todoItem: TodoItem) : TasksAction()
    data class UpdateDoneVisibility(val visible: Boolean): TasksAction()
    object UpdateRequest: TasksAction()
    object RefreshTasks: TasksAction()
    object SignOut: TasksAction()
}
