package com.niqr.todoapp.ui.taskEdit.model

sealed class TaskEditUiEvent {
    object NavigateBack: TaskEditUiEvent()
}