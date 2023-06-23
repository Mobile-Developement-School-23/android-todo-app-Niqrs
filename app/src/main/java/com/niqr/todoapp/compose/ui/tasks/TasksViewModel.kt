package com.niqr.todoapp.compose.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niqr.todoapp.data.TodoItemsRepository
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.compose.ui.tasks.model.TasksAction
import com.niqr.todoapp.compose.ui.tasks.model.TasksEvent
import com.niqr.todoapp.compose.ui.tasks.model.TasksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TodoItemsRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<TasksEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: TasksAction) {
        when(action) {
            is TasksAction.UpdateTask -> updateItem(action.todoItem)
            is TasksAction.DeleteTask -> deleteItem(action.id)
            is TasksAction.EditTask -> editTask(action.todoItem)
            is TasksAction.UpdateDoneVisibility -> updateDoneVisibility(action.visible)
        }
    }

    fun todoItems() =
        repository.todoItems()

    fun doneCount() =
        repository.doneCount()

    private fun editTask(item: TodoItem) {
        viewModelScope.launch {
            _uiEvent.send(TasksEvent.NavigateToEditTask(item.id))
        }
    }

    private fun updateItem(item: TodoItem) {
        viewModelScope.launch {
            repository.updateTodoItem(item)
        }
    }

    private fun deleteItem(id: String) {
        viewModelScope.launch {
            repository.deleteTodoItem(id)
        }
    }

    private fun updateDoneVisibility(visible: Boolean) {
        viewModelScope.launch {
            repository.updateDoneTodoItemsVisibility(visible)
        }
    }
}