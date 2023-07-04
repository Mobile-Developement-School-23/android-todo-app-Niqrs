package com.niqr.todoapp.ui.edit.model

import com.niqr.tasks.domain.model.Priority
import com.niqr.todoapp.utils.tomorrowLocalDate
import java.time.LocalDate

data class TaskEditUiState(
    val description: String = "",
    val priority: Priority = Priority.COMMON,
    val deadline: LocalDate = tomorrowLocalDate,
    val isDeadlineVisible: Boolean = false,
    val isEditing: Boolean = false
) {
    val isDeleteEnabled: Boolean
        get() = description.isNotBlank() || isEditing
}