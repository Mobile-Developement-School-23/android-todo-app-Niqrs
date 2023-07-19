package com.niqr.edit.ui.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalFocusManager
import com.niqr.edit.ui.model.TaskEditEvent
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskEditUiEventHandler(
    uiEvent: Flow<TaskEditEvent>,
    onNavigateUp: () -> Unit,
    onSave: () -> Unit,
    sheetState: ModalBottomSheetState
) {
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        uiEvent.collect {
            focusManager.clearFocus()
            when(it) {
                TaskEditEvent.PriorityChoose -> {
                    if (!sheetState.isVisible)
                        sheetState.show()
                }
                TaskEditEvent.NavigateBack -> {
                    onNavigateUp()
                }
                TaskEditEvent.SaveTask -> onSave()
            }
        }
    }
}
