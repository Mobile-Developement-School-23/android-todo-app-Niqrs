package com.niqr.edit.ui.model

import com.niqr.tasks.domain.model.Priority

/**
 * Contains info about edit ui actions
 */
sealed class TaskEditAction {
    data class DescriptionChange(val description: String) : TaskEditAction()
    data class UpdateDeadlineVisibility(val visible: Boolean): TaskEditAction()
    object PriorityChoose: TaskEditAction()
    data class UpdatePriority(val priority: Priority): TaskEditAction()
    data class UpdateDate(val date: Long): TaskEditAction()
    data class UpdateTime(val hour: Int, val minute: Int, val second: Int): TaskEditAction()
    object SaveTask: TaskEditAction()
    object NavigateUp: TaskEditAction()
    object DeleteTask : TaskEditAction()
}
