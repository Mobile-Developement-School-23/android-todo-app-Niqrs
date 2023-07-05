package com.niqr.edit.ui.model

sealed class TaskEditEvent {
    object NavigateBack: TaskEditEvent()
    object SaveTask: TaskEditEvent()
}
