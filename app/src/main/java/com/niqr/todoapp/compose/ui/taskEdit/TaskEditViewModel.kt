package com.niqr.todoapp.compose.ui.taskEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niqr.todoapp.compose.ui.taskEdit.model.TaskEditAction
import com.niqr.todoapp.compose.ui.taskEdit.model.TaskEditEvent
import com.niqr.todoapp.compose.ui.taskEdit.model.TaskEditUiState
import com.niqr.todoapp.data.TodoItemsRepository
import com.niqr.todoapp.data.model.Priority
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.utils.dateFromLong
import com.niqr.todoapp.utils.toLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
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

            else -> {}
        }
    }

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _priority = MutableStateFlow(Priority.COMMON)
    val priority = _priority.asStateFlow()

    private val _date = MutableStateFlow<LocalDate>(LocalDate.now().plusDays(1))
    val date = _date.asStateFlow()

    private val _dateVisibility = MutableStateFlow(false)
    val dateVisibility = _dateVisibility.asStateFlow()

    fun updateDescription(description: String) {
        _description.update {
            description
        }
    }

    fun updatePriority(priority: Priority) {
        _priority.update {
            priority
        }
    }

    fun updateDate(time: Long) {
        val newDate = dateFromLong(time)
        _date.update {
            newDate
        }
    }

    fun updateDateVisibility(visible: Boolean) {
        _dateVisibility.update {
            visible
        }
    }

    fun saveTask() {
        if (_description.value.isBlank())
            return

        val newTask = if (isEditing)
            previousTask!!.copy(
                description = _description.value,
                priority = _priority.value,
                deadline = if (_dateVisibility.value) _date.value else null,
                editedAt = LocalDateTime.now()
            )
        else
            TodoItem(
                id = "1",
                description = _description.value,
                priority = _priority.value,
                deadline = if (_dateVisibility.value) _date.value else null,
            )

        viewModelScope.launch {
            if (isEditing) repo.updateTodoItem(newTask)
            else repo.addTodoItem(newTask)
            _uiEvent.send(TaskEditEvent.NavigateBack)
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            if (isEditing)
                previousTask?.let { repo.deleteTodoItem(it.id) }
            _uiEvent.send(TaskEditEvent.NavigateBack)
        }
    }

    fun setupTask(id: String) {
        viewModelScope.launch {
            repo.findItemById(id)?.let { item ->
                isEditing = true
                previousTask = item
                updateDescription(item.description)
                updatePriority(item.priority)
                item.deadline?.let {
                    updateDate(it.toLong())
                    updateDateVisibility(true)
                }
            }
        }
    }
}