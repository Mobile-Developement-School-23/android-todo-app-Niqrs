package com.niqr.edit.ui.model

import com.niqr.edit.ui.utils.tomorrowLocalDate
import com.niqr.tasks.domain.model.Priority
import java.time.LocalDate

/**
 * Main state of edit screen
 */
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
