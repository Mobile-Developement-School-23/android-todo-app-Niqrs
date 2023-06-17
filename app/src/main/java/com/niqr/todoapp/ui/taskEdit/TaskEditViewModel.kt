package com.niqr.todoapp.ui.taskEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niqr.todoapp.data.TodoItemsRepository
import com.niqr.todoapp.data.model.Priority
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.utils.dateFromLong
import com.niqr.todoapp.utils.toLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        if (isEditing) {
            val newTask = previousTask!!.copy(
                description = _description.value,
                priority = _priority.value,
                deadline = if (_dateVisibility.value) _date.value else null,
                editedAt = LocalDateTime.now()
            )
            viewModelScope.launch {
                repo.updateTodoItem(newTask)
            }
        } else {
            val newTask = TodoItem(
                id = "1",
                description = _description.value,
                priority = _priority.value,
                deadline = if (_dateVisibility.value) _date.value else null,
            )
            viewModelScope.launch {
                repo.addTodoItem(newTask)
            }
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