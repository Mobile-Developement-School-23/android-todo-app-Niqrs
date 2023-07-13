package com.niqr.edit.ui.model

import com.niqr.tasks.domain.model.Priority

/**
 * Contains info about edit ui actions
 */
sealed class TaskEditAction {
    data class DescriptionChange(val description: String) : TaskEditAction()
    data class UpdateDeadlineVisibility(val visible: Boolean): TaskEditAction()
    object PriorityChoose: TaskEditAction()
    data class UpdatePriority(val priority: Priority) : TaskEditAction()
    data class UpdateDeadline(val deadline: Long) : TaskEditAction()

    object SaveTask: TaskEditAction()
    object NavigateUp: TaskEditAction()
    object DeleteTask : TaskEditAction()
}
