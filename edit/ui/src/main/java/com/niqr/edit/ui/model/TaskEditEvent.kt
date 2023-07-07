package com.niqr.edit.ui.model

/**
 * Provides info for edit ui events
 */
sealed class TaskEditEvent {
    object NavigateBack: TaskEditEvent()
    object SaveTask: TaskEditEvent()
}
