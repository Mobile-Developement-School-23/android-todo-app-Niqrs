package com.niqr.edit.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niqr.core.di.FeatureScope
import com.niqr.edit.ui.model.TaskEditAction
import com.niqr.edit.ui.model.TaskEditEvent
import com.niqr.edit.ui.model.TaskEditUiState
import com.niqr.edit.ui.utils.dateTimeFromLong
import com.niqr.tasks.domain.model.TodoItem
import com.niqr.tasks.domain.repo.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * An intermediate layer between edit ui and tasks logic
 *
 * Uses [TodoItemsRepository] to work with task
 */
@FeatureScope
class TaskEditViewModel @Inject constructor(
    private val repo: TodoItemsRepository
): ViewModel() {
    private var isEditing = false
    private var previousTask: TodoItem? = null

    private val _uiState = MutableStateFlow(TaskEditUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<TaskEditEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: TaskEditAction) {
        when(action) {
            is TaskEditAction.DescriptionChange -> _uiState.update {
                it.copy(description = action.description) }
            is TaskEditAction.UpdateDeadlineVisibility -> _uiState.update {
                it.copy(isDeadlineVisible = action.visible) }
            is TaskEditAction.PriorityChoose -> viewModelScope.launch {
                _uiEvent.send(TaskEditEvent.PriorityChoose) }
            is TaskEditAction.UpdatePriority -> _uiState.update {
                it.copy(priority = action.priority) }
            is TaskEditAction.UpdateDate -> updateDate(action.date)
            is TaskEditAction.UpdateTime -> updateTime(action.hour, action.minute, action.second)
            TaskEditAction.SaveTask -> saveTask()
            TaskEditAction.DeleteTask -> deleteTask()
            TaskEditAction.NavigateUp -> viewModelScope.launch {
                _uiEvent.send(TaskEditEvent.NavigateBack) }
        }
    }

    fun setupViewModel(arguments: Bundle?) {
        val taskId: String? = arguments?.getString(TaskId)
        taskId?.let {
            setupTask(taskId)
        }
    }

    private fun updateDate(date: Long) {
        _uiState.update {
            val prevDeadline = it.deadline
            val newDeadline = dateTimeFromLong(date)
                .withHour(prevDeadline.hour)
                .withMinute(prevDeadline.minute)

            uiState.value.copy(deadline = newDeadline)
        }
    }

    private fun updateTime(hour: Int, minute: Int, second: Int) {
        _uiState.update {
            val newDeadline = if (second != 0)
                    it.deadline.withHour(hour).withMinute(minute).withSecond(second)
                else
                    it.deadline.withHour(0).withMinute(0).withSecond(second)

            uiState.value.copy(deadline = newDeadline)
        }
    }

    private fun saveTask() {
        if (uiState.value.description.isBlank())
            return

        val newTask = if (isEditing)
            previousTask!!.copy(
                description = uiState.value.description,
                priority = uiState.value.priority,
                deadline = if (uiState.value.isDeadlineVisible) uiState.value.deadline else null
            )
        else
            TodoItem(
                description = uiState.value.description,
                priority = uiState.value.priority,
                deadline = if (uiState.value.isDeadlineVisible) uiState.value.deadline else null
            )

        viewModelScope.launch(Dispatchers.IO) {
            if (isEditing) repo.updateTodoItem(newTask)
            else repo.addTodoItem(newTask)
            _uiEvent.send(TaskEditEvent.SaveTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isEditing)
                previousTask?.let { repo.deleteTodoItem(it) }
            _uiEvent.send(TaskEditEvent.NavigateBack)
        }
    }

    private fun setupTask(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.findItemById(id)?.let { item ->
                isEditing = true
                previousTask = item

                withContext(Dispatchers.Main) {
                    _uiState.update { uiState.value.copy(
                        description = item.description,
                        priority = item.priority,
                        deadline = item.deadline ?: uiState.value.deadline,
                        isDeadlineVisible = item.deadline != null,
                        isEditing = true
                    ) }
                }
            }
        }
    }
}
