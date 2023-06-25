package com.niqr.todoapp.ui.taskEdit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.niqr.todoapp.ui.taskEdit.components.TaskEditDateField
import com.niqr.todoapp.ui.taskEdit.components.TaskEditDeleteButton
import com.niqr.todoapp.ui.taskEdit.components.TaskEditDivider
import com.niqr.todoapp.ui.taskEdit.components.TaskEditPriorityField
import com.niqr.todoapp.ui.taskEdit.components.TaskEditTextField
import com.niqr.todoapp.ui.taskEdit.components.TaskEditTopAppBar
import com.niqr.todoapp.ui.taskEdit.components.TaskEditUiEventHandler
import com.niqr.todoapp.ui.taskEdit.model.TaskEditAction
import com.niqr.todoapp.ui.taskEdit.model.TaskEditEvent
import com.niqr.todoapp.ui.taskEdit.model.TaskEditUiState
import com.niqr.todoapp.ui.theme.ExtendedTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun TaskEditScreen(
    uiState: TaskEditUiState,
    uiEvent: Flow<TaskEditEvent>,
    onAction: (TaskEditAction) -> Unit,
    onNavigateUp: () -> Unit,
    onSave: () -> Unit
) {
    TaskEditUiEventHandler(uiEvent, onNavigateUp, onSave)

    Scaffold(
        topBar = {
            TaskEditTopAppBar(uiState.description, onAction)
        },
        containerColor = ExtendedTheme.colors.backPrimary
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                TaskEditTextField(
                    description = uiState.description,
                    onAction = onAction
                )

                TaskEditPriorityField(uiState.priority, onAction)

                TaskEditDivider(PaddingValues(horizontal = 16.dp))

                TaskEditDateField(
                    date = uiState.deadline,
                    isDateVisible = uiState.isDeadlineVisible,
                    onAction = onAction
                )

                TaskEditDivider(PaddingValues(top = 16.dp, bottom = 8.dp))

                TaskEditDeleteButton(
                    enabled = uiState.isDeleteEnabled,
                    onAction = onAction
                )
            }
            item {
                Spacer(modifier = Modifier.height(96.dp))
            }
        }
    }
}