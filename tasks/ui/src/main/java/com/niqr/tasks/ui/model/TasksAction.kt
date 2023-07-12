package com.niqr.tasks.ui.model

import com.niqr.settings.domain.model.Theme
import com.niqr.tasks.domain.model.TodoItem
import com.niqr.tasks.ui.TasksScreen


/**
 * Contains info about tasks ui actions on [TasksScreen]
 */
sealed class TasksAction {
    object CreateTask: TasksAction()
    data class UpdateTask(val todoItem: TodoItem): TasksAction()
    data class DeleteTask(val todoItem: TodoItem) : TasksAction()
    data class EditTask(val todoItem: TodoItem) : TasksAction()
    data class UpdateDoneVisibility(val visible: Boolean): TasksAction()
    object ShowSettings: TasksAction()
    data class UpdateTheme(val theme: Theme): TasksAction()
    object UpdateRequest: TasksAction()
    object RefreshTasks: TasksAction()
    object SignOut: TasksAction()
}
