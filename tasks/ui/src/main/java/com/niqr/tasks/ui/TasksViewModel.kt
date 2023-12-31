package com.niqr.tasks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niqr.auth.domain.AuthRepository
import com.niqr.core.di.FeatureScope
import com.niqr.settings.domain.model.Theme
import com.niqr.settings.domain.settings.AppSettingsMutableProvider
import com.niqr.tasks.domain.model.TodoItem
import com.niqr.tasks.domain.repo.TodoItemsRepository
import com.niqr.tasks.ui.model.TasksAction
import com.niqr.tasks.ui.model.TasksEvent
import com.niqr.tasks.ui.model.TasksUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * An intermediate layer between tasks ui and tasks logic
 *
 * Uses [AuthRepository] to control user
 *
 * Uses [TodoItemsRepository] for getting and editing [TodoItem]s
 */
@FeatureScope
class TasksViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val todoRepo: TodoItemsRepository,
    private val settingsProvider: AppSettingsMutableProvider
): ViewModel() {
    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState = _uiState.asStateFlow()

    init {
        setupViewModel()
    }

    private var lastDeleted = TodoItem(description = "dummy item")

    private val _uiEvent = Channel<TasksEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: TasksAction) {
        when(action) {
            is TasksAction.CreateTask -> viewModelScope.launch { _uiEvent.send(TasksEvent.NavigateToNewTask) }
            is TasksAction.UpdateTask -> updateItem(action.todoItem)
            is TasksAction.DeleteTask -> deleteItem(action.todoItem)
            is TasksAction.EditTask -> editTask(action.todoItem)
            is TasksAction.UpdateDoneVisibility -> updateDoneVisibility(action.visible)
            is TasksAction.UndoAction -> returnTask()
            is TasksAction.ShowSettings -> viewModelScope.launch { _uiEvent.send(TasksEvent.ShowSettings) }
            is TasksAction.UpdateTheme -> updateTheme(action.theme)
            is TasksAction.UpdateRequest -> viewModelScope.launch(Dispatchers.IO) { todoRepo.updateTodoItems() }
            is TasksAction.RefreshTasks -> refreshTasks()
            is TasksAction.SignOut -> signOut()
        }
    }

    private fun setupViewModel() {
        viewModelScope.launch {
            combine(
                todoRepo.todoItems(),
                todoRepo.doneVisible(),
                settingsProvider.settingsFlow()
            ) { tasks, doneVisible, settings ->
                val newTasks = when(doneVisible) {
                    true -> tasks
                    else -> tasks.filter { !it.isDone }
                }.sortedBy { it.createdAt }
                Triple(doneVisible, newTasks, settings)
            }.collectLatest { triple ->
                _uiState.update {
                    uiState.value.copy(
                        doneVisible = triple.first,
                        tasks = triple.second,
                        selectedTheme = triple.third.theme
                    )
                }
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
            lastDeleted = item
            todoRepo.deleteTodoItem(item)
            _uiEvent.send(TasksEvent.UndoNotification)
        }
    }

    private fun updateDoneVisibility(visible: Boolean) {
        _uiState.update { uiState.value.copy(doneVisible = visible) }
        viewModelScope.launch(Dispatchers.IO) {
            todoRepo.updateDoneTodoItemsVisibility(visible)
        }
    }

    private fun returnTask() {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepo.addTodoItem(lastDeleted)
        }
    }

    private fun updateTheme(theme: Theme) {
        viewModelScope.launch {
            settingsProvider.updateTheme(theme)
        }
    }

    private fun refreshTasks() {
        viewModelScope.launch {
            _uiState.update { uiState.value.copy(isRefreshing = true) }
            if (!todoRepo.refreshTodoItems())
                _uiEvent.send(TasksEvent.ConnectionError)
            _uiState.update { uiState.value.copy(isRefreshing = false) }
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
