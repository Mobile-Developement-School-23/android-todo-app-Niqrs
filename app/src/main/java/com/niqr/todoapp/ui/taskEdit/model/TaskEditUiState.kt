package com.niqr.todoapp.ui.taskEdit.model

import com.niqr.todoapp.data.model.Priority
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