package com.niqr.edit.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.niqr.core.ui.theme.Blue
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.edit.ui.R
import com.niqr.edit.ui.model.TaskEditAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditTopAppBar(
    description: String,
    elevation: Dp,
    onAction: (TaskEditAction) -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .shadow(elevation),
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
                    style = ExtendedTheme.typography.button
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ExtendedTheme.colors.backPrimary,
            scrolledContainerColor = ExtendedTheme.colors.backElevated,
            navigationIconContentColor = ExtendedTheme.colors.labelPrimary
        )
    )
}
