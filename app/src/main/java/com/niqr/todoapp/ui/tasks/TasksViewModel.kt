package com.niqr.todoapp.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niqr.todoapp.data.TodoItemsRepository
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.ui.tasks.model.TasksUiAction
import com.niqr.todoapp.ui.tasks.model.TasksUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TodoItemsRepository
): ViewModel() {

    private val _uiEvent = Channel<TasksUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onUiAction(action: TasksUiAction) {
        when(action) {
            is TasksUiAction.UpdateTask -> updateItem(action.todoItem)
            is TasksUiAction.DeleteTask -> deleteItemAtPosition(action.position)
            is TasksUiAction.EditTask -> editTask(action.todoItem)
        }
    }

    fun todoItems() =
        repository.todoItems()

    private fun editTask(item: TodoItem) {
        viewModelScope.launch {
            _uiEvent.send(TasksUiEvent.NavigateToEditTask(item.id))
        }
    }

    private fun updateItem(item: TodoItem) {
        viewModelScope.launch {
            repository.updateTodoItem(item)
        }
    }

    private fun deleteItemAtPosition(position: Int) {
        viewModelScope.launch {
            repository.deleteTodoItemAt(position)
        }
    }

    private fun deleteItem(id: String) {
        viewModelScope.launch {
            repository.deleteTodoItem(id)
        }
    }
}