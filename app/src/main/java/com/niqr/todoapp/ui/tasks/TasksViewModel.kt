package com.niqr.todoapp.ui.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niqr.todoapp.data.abstraction.AuthRepository
import com.niqr.todoapp.data.abstraction.TodoItemsRepository
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.ui.tasks.model.TasksAction
import com.niqr.todoapp.ui.tasks.model.TasksEvent
import com.niqr.todoapp.ui.tasks.model.TasksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TasksViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val todoRepo: TodoItemsRepository
): ViewModel() {
    var uiState by mutableStateOf(TasksUiState())
        private set

    init {
        setupTodoItems()
    }

    private val _uiEvent = Channel<TasksEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: TasksAction) {
        when(action) {
            TasksAction.CreateTask -> viewModelScope.launch { _uiEvent.send(TasksEvent.NavigateToNewTask) }
            is TasksAction.UpdateTask -> updateItem(action.todoItem)
            is TasksAction.DeleteTask -> deleteItem(action.todoItem)
            is TasksAction.EditTask -> editTask(action.todoItem)
            is TasksAction.UpdateDoneVisibility -> updateDoneVisibility(action.visible)
            is TasksAction.UpdateRequest -> viewModelScope.launch(Dispatchers.IO) { todoRepo.updateTodoItems() }
            is TasksAction.RefreshTasks -> refreshTasks()
            TasksAction.SignOut -> signOut()
        }
    }

    private fun setupTodoItems() {
        viewModelScope.launch {
            todoRepo.todoItems().combine(todoRepo.doneVisible()) { tasks, doneVisible ->
                val newTasks = when(doneVisible) {
                    true -> tasks
                    else -> tasks.filter { !it.isDone }
                }
                Pair(doneVisible, newTasks)
            }.collectLatest {
                uiState = uiState.copy(doneVisible = it.first, tasks = it.second)
            }
        }
    }

    private fun editTask(item: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvent.send(TasksEvent.NavigateToEditTask(item.id))
        }
    }

    private fun updateItem(item: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepo.updateTodoItem(item)
        }
    }

    private fun deleteItem(item: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepo.deleteTodoItem(item)
        }
    }

    private fun updateDoneVisibility(visible: Boolean) {
        uiState = uiState.copy(doneVisible = visible)
        viewModelScope.launch(Dispatchers.IO) {
            todoRepo.updateDoneTodoItemsVisibility(visible)
        }
    }

    private fun refreshTasks() {
        viewModelScope.launch {
            uiState = uiState.copy(isRefreshing = true)
            if (!todoRepo.refreshTodoItems())
                _uiEvent.send(TasksEvent.ConnectionError)
            uiState = uiState.copy(isRefreshing = false)
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                todoRepo.pushTodoItems()
            }
            delay(100)
            authRepo.signOut()
            todoRepo.clearTodoItems()
            _uiEvent.send(TasksEvent.SignOut)
        }
    }
}
