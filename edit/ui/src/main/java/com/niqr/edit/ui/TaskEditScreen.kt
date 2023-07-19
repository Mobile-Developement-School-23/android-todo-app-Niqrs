package com.niqr.edit.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.components.TodoBottomSheetLayout
import com.niqr.core.ui.components.rememberTodoBottomSheetState
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.TodoAppTheme
import com.niqr.edit.ui.components.PriorityBottomSheetContent
import com.niqr.edit.ui.components.TaskEditDeleteButton
import com.niqr.edit.ui.components.TaskEditDivider
import com.niqr.edit.ui.components.TaskEditPriorityField
import com.niqr.edit.ui.components.TaskEditTextField
import com.niqr.edit.ui.components.TaskEditTimeField
import com.niqr.edit.ui.components.TaskEditTopAppBar
import com.niqr.edit.ui.components.TaskEditUiEventHandler
import com.niqr.edit.ui.model.TaskEditAction
import com.niqr.edit.ui.model.TaskEditEvent
import com.niqr.edit.ui.model.TaskEditUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Screen to create/edit task
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskEditScreen(
    uiState: TaskEditUiState,
    uiEvent: Flow<TaskEditEvent>,
    onAction: (TaskEditAction) -> Unit,
    onNavigateUp: () -> Unit,
    onSave: () -> Unit
) {
    val listState = rememberLazyListState()
    val sheetState = rememberTodoBottomSheetState()

    TaskEditUiEventHandler(uiEvent, onNavigateUp, onSave, sheetState)

    TodoBottomSheetLayout(
        sheetContent = {
            PriorityBottomSheetContent(uiState.priority, onAction)
        },
        sheetState = sheetState
    ) {
        val topBarElevation by animateDpAsState(
            if (listState.canScrollBackward) 8.dp else 0.dp,
            label = "top bar elevation"
        )

        Scaffold(
            topBar = {
                TaskEditTopAppBar(uiState.description, topBarElevation, onAction)
            },
            containerColor = ExtendedTheme.colors.backPrimary
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                state = listState
            ) {
                item {
                    TaskEditTextField(
                        description = uiState.description,
                        onAction = onAction
                    )

                    TaskEditPriorityField(uiState.priority, onAction)

                    TaskEditDivider(PaddingValues(horizontal = 16.dp))

                    TaskEditTimeField(
                        time = uiState.deadline,
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
}

@Preview
@Composable
private fun TaskEditScreenPreview() {
    TodoAppTheme {
        TaskEditScreen(
            uiState = TaskEditUiState(),
            uiEvent = emptyFlow(),
            onAction = {},
            onNavigateUp = {},
            onSave = {}
        )
    }
}