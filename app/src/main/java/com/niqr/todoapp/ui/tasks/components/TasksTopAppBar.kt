package com.niqr.todoapp.ui.tasks.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.niqr.todoapp.R
import com.niqr.todoapp.ui.tasks.model.TasksAction
import com.niqr.todoapp.ui.theme.Blue
import com.niqr.todoapp.ui.theme.ExtendedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksTopAppBar(
    doneVisible: Boolean,
    onAction: (TasksAction) -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.my_tasks))
        },
        actions = {
            IconButton(
                onClick = {
                    onAction(TasksAction.UpdateDoneVisibility(!doneVisible))
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (doneVisible) R.drawable.ic_baseline_visibility_24dp
                        else R.drawable.ic_baseline_visibility_off_24dp
                    ),
                    contentDescription = null,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ExtendedTheme.colors.backPrimary,
            titleContentColor = ExtendedTheme.colors.labelPrimary,
            actionIconContentColor = Blue
        )
    )
}