package com.niqr.todoapp.ui.taskEdit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niqr.todoapp.data.abstraction.TodoItemsRepository
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.ui.taskEdit.model.TaskEditAction
import com.niqr.todoapp.ui.taskEdit.model.TaskEditEvent
import com.niqr.todoapp.ui.taskEdit.model.TaskEditUiState
import com.niqr.todoapp.utils.dateFromLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: TodoItemsRepository
): ViewModel() {
    private var isEditing = false
    private var previousTask: TodoItem? = null

    init {
        val taskId: String? = savedStateHandle[TaskId]
        taskId?.let {
            setupTask(taskId)
        }
    }

    var uiState by mutableStateOf(TaskEditUiState())
        private set

    private val _uiEvent = Channel<TaskEditEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: TaskEditAction) {
        when(action) {
            is TaskEditAction.DescriptionChange -> uiState = uiState.copy(description = action.description)
            is TaskEditAction.UpdateDeadlineVisibility -> uiState = uiState.copy(isDeadlineVisible = action.visible)
            is TaskEditAction.UpdatePriority -> uiState = uiState.copy(priority = action.priority)
            is TaskEditAction.UpdateDeadline -> uiState = uiState.copy(deadline = dateFromLong(action.deadline))
            TaskEditAction.SaveTask -> saveTask()
            TaskEditAction.DeleteTask -> deleteTask()
            TaskEditAction.NavigateUp -> viewModelScope.launch { _uiEvent.send(TaskEditEvent.NavigateBack) }
        }
    }

    private fun saveTask() {
        if (uiState.description.isBlank())
            return

        val newTask = if (isEditing)
            previousTask!!.copy(
                description = uiState.description,
                priority = uiState.priority,
                deadline = if (uiState.isDeadlineVisible) uiState.deadline else null,
                editedAt = LocalDateTime.now(ZoneOffset.UTC)
            )
        else
            TodoItem(
                description = uiState.description,
                priority = uiState.priority,
                deadline = if (uiState.isDeadlineVisible) uiState.deadline else null,
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
                    uiState = uiState.copy(
                        description = item.description,
                        priority = item.priority,
                        deadline = item.deadline ?: uiState.deadline,
                        isDeadlineVisible = item.deadline != null,
                        isEditing = true
                    )
                }
            }
        }
    }
}