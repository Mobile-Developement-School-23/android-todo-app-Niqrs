package com.niqr.todoapp.ui.tasks

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.niqr.todoapp.ui.tasks.components.TasksFloatingActionButton
import com.niqr.todoapp.ui.tasks.components.TasksItem
import com.niqr.todoapp.ui.tasks.components.TasksTopAppBar
import com.niqr.todoapp.ui.tasks.components.TasksUiEventHandler
import com.niqr.todoapp.ui.tasks.model.TasksAction
import com.niqr.todoapp.ui.tasks.model.TasksEvent
import com.niqr.todoapp.ui.tasks.model.TasksUiState
import com.niqr.todoapp.ui.theme.ExtendedTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun TasksScreen(
    uiState: TasksUiState,
    uiEvent: Flow<TasksEvent>,
    onAction: (TasksAction) -> Unit,
    onCreateTask: () -> Unit,
    onEditTask: (String) -> Unit
) {
    TasksUiEventHandler(uiEvent, onCreateTask, onEditTask)

    Scaffold(
        topBar = {
            TasksTopAppBar(uiState.doneVisible, onAction)
        },
        floatingActionButton = {
            TasksFloatingActionButton(onAction)
        },
        containerColor = ExtendedTheme.colors.backPrimary
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(uiState.tasks, key = { it.id }) {
                TasksItem(
                    task = it,
                    onAction = onAction
                )
            }
            item {
                Spacer(modifier = Modifier.height(96.dp))
            }
        }
    }
}