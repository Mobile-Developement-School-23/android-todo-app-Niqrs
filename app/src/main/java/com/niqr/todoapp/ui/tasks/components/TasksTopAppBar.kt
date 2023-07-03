package com.niqr.todoapp.ui.tasks.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.niqr.todoapp.R
import com.niqr.todoapp.ui.tasks.model.TasksAction
import com.niqr.core.ui.theme.Blue
import com.niqr.core.ui.theme.ExtendedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksTopAppBar(
    doneVisible: Boolean,
    onAction: (TasksAction) -> Unit
) {
    var settingsVisible by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.my_tasks))
        },
        navigationIcon = {
            SettingsMenu(
                expanded = settingsVisible,
                hideMenu = { settingsVisible = false },
                onAction = onAction
            )

            IconButton(
                onClick = {
                    settingsVisible = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                )
            }
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
            navigationIconContentColor = ExtendedTheme.colors.labelPrimary,
            titleContentColor = ExtendedTheme.colors.labelPrimary,
            actionIconContentColor = Blue
        )
    )
}

@Composable
private fun SettingsMenu(
    expanded: Boolean,
    hideMenu: () -> Unit,
    onAction: (TasksAction) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = hideMenu,
        modifier = Modifier
            .background(ExtendedTheme.colors.backElevated),
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.sign_out)) },
            onClick = {
                onAction(TasksAction.SignOut)
                hideMenu()
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null
                )
            },
            colors = MenuDefaults.itemColors(
                textColor = ExtendedTheme.colors.labelPrimary,
                leadingIconColor = ExtendedTheme.colors.labelPrimary
            )
        )
    }
}