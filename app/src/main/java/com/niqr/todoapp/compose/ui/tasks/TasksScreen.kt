package com.niqr.todoapp.compose.ui.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.niqr.todoapp.compose.ui.tasks.model.TasksAction
import com.niqr.todoapp.compose.ui.tasks.model.TasksEvent
import com.niqr.todoapp.compose.ui.tasks.model.TasksUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TasksScreen(
    uiState: StateFlow<TasksUiState>,
    uiEvent: Flow<TasksEvent>,
    onAction: (TasksAction) -> Unit,
    onCreateTask: () -> Unit,
    onEditTask: () -> Unit,
) {
    Column {
        Text(text = "Tasks Screen")
        Button(onClick = onCreateTask) {
            Text(text = "Navigate to create task")
        }
    }
}