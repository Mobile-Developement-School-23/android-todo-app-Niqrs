package com.niqr.todoapp.ui.edit.model

sealed class TaskEditEvent {
    object NavigateBack: TaskEditEvent()
    object SaveTask: TaskEditEvent()
}