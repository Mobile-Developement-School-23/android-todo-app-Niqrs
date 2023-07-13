package com.niqr.tasks.ui.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.work.WorkManager
import com.niqr.other.work.utils.SYNCHRONIZATION_WORK_TAG
import com.niqr.tasks.ui.R
import com.niqr.tasks.ui.model.TasksAction
import com.niqr.tasks.ui.model.TasksEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TasksUiEventHandler(
    uiEvent: Flow<TasksEvent>,
    onCreateTask: () -> Unit,
    onAction: (TasksAction) -> Unit,
    onEditTask: (String) -> Unit,
    onSignOut: () -> Unit,
    snackbarHostState: SnackbarHostState,
    sheetState: ModalBottomSheetState
) {
    val context = LocalContext.current
    var launchedBefore by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!launchedBefore) {
            val workManager = WorkManager.getInstance(context)
            val workInfo = workManager
                .getWorkInfosByTag(SYNCHRONIZATION_WORK_TAG).get()

            if (workInfo.firstOrNull {!it.state.isFinished} == null)
                onAction(TasksAction.UpdateRequest)

            launchedBefore = true
        }
    }

    LaunchedEffect(Unit) {
        uiEvent.collect {
            when(it) {
                TasksEvent.ConnectionError -> {
                    launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.connection_error))

                    }
                }
                TasksEvent.UndoNotification -> {
                    launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        val snackbarResult = snackbarHostState.showSnackbar(
                            message = context.getString(R.string.task_deleted),
                            actionLabel = context.getString(R.string.undo),
                            duration = SnackbarDuration.Short
                        )
                        when(snackbarResult) {
                            SnackbarResult.Dismissed -> Unit
                            SnackbarResult.ActionPerformed -> {
                                onAction(TasksAction.UndoAction)
                            }
                        }
                    }
                }
                TasksEvent.ShowSettings -> {
                    if (!sheetState.isVisible)
                        sheetState.show()
                }
                is TasksEvent.NavigateToEditTask -> onEditTask(it.id)
                TasksEvent.NavigateToNewTask -> onCreateTask()
                TasksEvent.SignOut -> onSignOut()
            }
        }
    }
}
