package com.niqr.tasks.ui.components

import androidx.compose.material3.SnackbarHostState
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

@Composable
fun TasksUiEventHandler(
    uiEvent: Flow<TasksEvent>,
    onCreateTask: () -> Unit,
    onAction: (TasksAction) -> Unit,
    onEditTask: (String) -> Unit,
    onSignOut: () -> Unit,
    snackbarHostState: SnackbarHostState
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
                    snackbarHostState.showSnackbar(context.getString(R.string.connection_error))
                }
                is TasksEvent.NavigateToEditTask -> onEditTask(it.id)
                TasksEvent.NavigateToNewTask -> onCreateTask()
                TasksEvent.SignOut -> onSignOut()
            }
        }
    }
}