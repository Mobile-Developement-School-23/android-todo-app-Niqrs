package com.niqr.edit.ui.model

import com.niqr.edit.ui.utils.tomorrowLocalDateTime
import com.niqr.tasks.domain.model.Priority
import java.time.LocalDateTime

/**
 * Main state of edit screen
 */
data class TaskEditUiState(
    val description: String = "",
    val priority: Priority = Priority.COMMON,
    val deadline: LocalDateTime = tomorrowLocalDateTime,
    val isDeadlineVisible: Boolean = false,
    val isEditing: Boolean = false
) {
    val isDeleteEnabled: Boolean
        get() = description.isNotBlank() || isEditing
}
