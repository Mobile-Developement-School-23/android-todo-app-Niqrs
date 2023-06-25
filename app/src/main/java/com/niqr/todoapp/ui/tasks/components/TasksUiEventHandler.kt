package com.niqr.todoapp.ui.tasks.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.niqr.todoapp.ui.tasks.model.TasksEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun TasksUiEventHandler(
    uiEvent: Flow<TasksEvent>,
    onCreateTask: () -> Unit,
    onEditTask: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        uiEvent.collect {
            when(it) {
                is TasksEvent.NavigateToEditTask -> onEditTask(it.id)
                TasksEvent.NavigateToNewTask -> onCreateTask()
            }
        }
    }
}