package com.niqr.todoapp.compose.ui.taskEdit

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.niqr.todoapp.compose.ui.taskEdit.model.TaskEditAction
import com.niqr.todoapp.compose.ui.taskEdit.model.TaskEditEvent
import com.niqr.todoapp.compose.ui.taskEdit.model.TaskEditUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TaskEditScreen(
    uiState: StateFlow<TaskEditUiState>,
    uiEvent: Flow<TaskEditEvent>,
    onAction: (TaskEditAction) -> Unit,
    onNavigateUp: () -> Unit,
    onSave: () -> Unit,
) {
    Column {
        Text(text = "TaskEdit Screen")
        Button(onClick = onNavigateUp) {
            Text(text = "Navigate back")
        }
    }
}