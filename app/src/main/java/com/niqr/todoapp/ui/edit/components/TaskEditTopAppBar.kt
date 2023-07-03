package com.niqr.todoapp.ui.edit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.niqr.todoapp.R
import com.niqr.todoapp.ui.edit.model.TaskEditAction
import com.niqr.todoapp.ui.theme.Blue
import com.niqr.todoapp.ui.theme.ExtendedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditTopAppBar(
    description: String,
    onAction: (TaskEditAction) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { onAction(TaskEditAction.NavigateUp) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                )
            }
        },
        title = { /* There is no title, but i can't remove it :) */ },
        actions = {
            val saveButtonColor by animateColorAsState(
                targetValue = if (description.isNotBlank()) Blue else ExtendedTheme.colors.labelDisable,
                label = "button color animation"
            )

            TextButton(
                onClick = { onAction(TaskEditAction.SaveTask) },
                enabled = description.isNotBlank(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = saveButtonColor,
                    disabledContentColor = saveButtonColor
                )
            ) {
                Text(
                    text = stringResource(R.string.save_button),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ExtendedTheme.colors.backPrimary,
            navigationIconContentColor = ExtendedTheme.colors.labelPrimary
        )
    )
}