package com.niqr.tasks.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.components.TodoBottomSheetLayout
import com.niqr.core.ui.theme.Blue
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.tasks.domain.model.TodoItem
import com.niqr.tasks.ui.components.SettingsBottomSheetContent
import com.niqr.tasks.ui.components.TasksFloatingActionButton
import com.niqr.tasks.ui.components.TasksItem
import com.niqr.tasks.ui.components.TasksTopAppBar
import com.niqr.tasks.ui.components.TasksUiEventHandler
import com.niqr.tasks.ui.components.pullRefresh.PullRefreshIndicator
import com.niqr.tasks.ui.components.pullRefresh.pullRefresh
import com.niqr.tasks.ui.components.pullRefresh.rememberPullRefreshState
import com.niqr.tasks.ui.model.TasksAction
import com.niqr.tasks.ui.model.TasksEvent
import com.niqr.tasks.ui.model.TasksUiState
import kotlinx.coroutines.flow.Flow

/**
 * Main app screen for watching and editing all user [TodoItem]s
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TasksScreen(
    uiState: TasksUiState,
    uiEvent: Flow<TasksEvent>,
    onAction: (TasksAction) -> Unit,
    onCreateTask: () -> Unit,
    onEditTask: (String) -> Unit,
    onSignOut: () -> Unit
) {
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = { onAction(TasksAction.RefreshTasks) }
    )

    TasksUiEventHandler(
        uiEvent = uiEvent,
        onCreateTask = onCreateTask,
        onAction = onAction,
        onEditTask = onEditTask,
        onSignOut = onSignOut,
        snackbarHostState = snackbarHostState,
        sheetState = sheetState
    )



    TodoBottomSheetLayout(
        sheetContent = {
            SettingsBottomSheetContent(uiState.selectedTheme, onAction)
        },
        sheetState = sheetState,
    ) {
        val topBarElevation by animateDpAsState(
            if (listState.canScrollBackward) 8.dp else 0.dp,
            label = "top bar elevation"
        )

        Scaffold(
            topBar = {
                TasksTopAppBar(uiState.doneVisible, topBarElevation, onAction)
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState) {
                    Snackbar(
                        snackbarData = it,
                        actionColor = Blue
                    )
                }
            },
            floatingActionButton = {
                TasksFloatingActionButton(onAction)
            },
            containerColor = ExtendedTheme.colors.backPrimary
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState
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

                PullRefreshIndicator(
                    refreshing = uiState.isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}