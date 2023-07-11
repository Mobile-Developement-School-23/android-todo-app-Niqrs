package com.niqr.edit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.GrayLight
import com.niqr.core.ui.theme.Red
import com.niqr.edit.ui.R
import com.niqr.edit.ui.model.TaskEditAction
import com.niqr.edit.ui.utils.toStringResource
import com.niqr.tasks.domain.model.Priority

@Composable
fun TaskEditPriorityField(
    priority: Priority,
    onAction: (TaskEditAction) -> Unit
) {
    val isHighPriority = remember(priority) { priority == Priority.HIGH }
    var menuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(top = 24.dp, bottom = 12.dp)
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(6.dp))
            .clickable { menuExpanded = true }
            .padding(4.dp)
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides ExtendedTheme.typography.body
        ) {
            Text(
                text = stringResource(id = R.string.priority),
                color = ExtendedTheme.colors.labelPrimary
            )
            Text(
                text = stringResource(id = priority.toStringResource()),
                modifier = Modifier.padding(top = 2.dp),
                color = if (isHighPriority) Red else ExtendedTheme.colors.labelTertiary,
                style = ExtendedTheme.typography.subhead
            )

            PriorityDropdownMenu(
                expanded = menuExpanded,
                hideMenu = { menuExpanded = false },
                onAction = onAction
            )
        }
    }
}

@Composable
private fun PriorityDropdownMenu(
    expanded: Boolean,
    hideMenu: () -> Unit,
    onAction: (TaskEditAction) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = hideMenu,
        modifier = Modifier
            .background(ExtendedTheme.colors.backElevated),
        offset = DpOffset(x = 52.dp, y = (-18).dp)
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.priority_no)) },
            onClick = {
                onAction(TaskEditAction.UpdatePriority(Priority.COMMON))
                hideMenu()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_remove_24dp),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            colors = MenuDefaults.itemColors(
                textColor = ExtendedTheme.colors.labelPrimary,
                leadingIconColor = GrayLight
            )
        )

        DropdownMenuItem(
            text = { Text(stringResource(R.string.priority_low)) },
            onClick = {
                onAction(TaskEditAction.UpdatePriority(Priority.LOW))
                hideMenu()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_priority_low_24dp),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            colors = MenuDefaults.itemColors(
                textColor = ExtendedTheme.colors.labelPrimary,
                leadingIconColor = GrayLight
            )
        )

        DropdownMenuItem(
            text = { Text(stringResource(R.string.priority_high)) },
            onClick = {
                onAction(TaskEditAction.UpdatePriority(Priority.HIGH))
                hideMenu()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_priority_high_24dp),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            colors = MenuDefaults.itemColors(
                textColor = Red,
                leadingIconColor = Red
            )
        )
    }
}