package com.niqr.todoapp.compose.ui.taskEdit.model

sealed class TaskEditEvent {
    object NavigateBack: TaskEditEvent()
}