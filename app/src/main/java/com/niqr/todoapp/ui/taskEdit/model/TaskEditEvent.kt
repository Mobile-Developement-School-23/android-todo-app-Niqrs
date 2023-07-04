package com.niqr.todoapp.ui.taskEdit.model

sealed class TaskEditEvent {
    object NavigateBack: TaskEditEvent()
    object SaveTask: TaskEditEvent()
}