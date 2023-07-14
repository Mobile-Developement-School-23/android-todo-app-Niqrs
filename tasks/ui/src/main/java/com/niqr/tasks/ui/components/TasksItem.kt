package com.niqr.tasks.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.Gray
import com.niqr.core.ui.theme.GrayLight
import com.niqr.core.ui.theme.Green
import com.niqr.core.ui.theme.Red
import com.niqr.core.ui.theme.White
import com.niqr.tasks.domain.model.Priority
import com.niqr.tasks.domain.model.TodoItem
import com.niqr.tasks.ui.R
import com.niqr.tasks.ui.model.TasksAction
import com.niqr.tasks.ui.utils.toStringDate
import com.niqr.tasks.ui.utils.toStringTime
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@Composable
fun LazyItemScope.TasksItem(
    task: TodoItem,
    onAction: (TasksAction) -> Unit
) {
    val isHighPriority = remember(task.priority) { task.priority == Priority.HIGH }

    val currentTask by rememberUpdatedState(task)
    val dismissState = rememberDismissState(
        confirmValueChange = {
            when(it) {
                DismissValue.DismissedToEnd -> {
                    onAction(TasksAction.UpdateTask(currentTask.copy(isDone = !currentTask.isDone)))
                    false
                }
                DismissValue.DismissedToStart -> {
                    onAction(TasksAction.DeleteTask(currentTask))
                    true
                }
                else -> false
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateItemPlacement()
    ) {
        SwipeToDismiss(
            state = dismissState,
            background = {
                SwipeBackground(dismissState)
            },
            dismissContent = {
                var menuExpanded by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .combinedClickable(
                            onClick = { onAction(TasksAction.EditTask(task)) },
                            onLongClick = { menuExpanded = true }
                        )
                        .background(ExtendedTheme.colors.backPrimary)
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 6.dp)
                ) {

                    TasksItemCheckbox(
                        isDone = task.isDone,
                        isHighPriority = isHighPriority,
                        onCheckedChange = {
                            onAction(TasksAction.UpdateTask(task.copy(isDone = !task.isDone)))
                        }
                    )

                    TasksItemIcon(task.priority, isHighPriority)


                    Column(
                        Modifier
                            .weight(1f)
                            .padding(top = 12.dp)
                    ) {
                        TasksItemText(task.isDone, task.description, task.deadline)

                        Box(modifier = Modifier.align(Alignment.End)) {
                            CompositionLocalProvider(LocalTextStyle provides ExtendedTheme.typography.body) {
                                TasksItemMenu(
                                    task = task,
                                    expanded = menuExpanded,
                                    hideMenu = { menuExpanded = false },
                                    onAction = onAction
                                )
                            }
                        }
                    }

                    Icon(
                        modifier = Modifier
                            .padding(top = 10.dp, end = 10.dp, start = 6.dp)
                            .size(26.dp),
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = ExtendedTheme.colors.labelTertiary
                    )
                }
            }
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            color = ExtendedTheme.colors.supportOverlay
        )
    }
}

@Composable
private fun TasksItemCheckbox(
    isDone: Boolean,
    isHighPriority: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Checkbox(
        checked = isDone,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkedColor = Green,
            uncheckedColor = if (isHighPriority) Red else ExtendedTheme.colors.supportSeparator
        )
    )
}

@Composable
private fun TasksItemIcon(
    priority: Priority,
    isHighPriority: Boolean
) {
    if (priority != Priority.COMMON) {
        if (isHighPriority)
            Icon(
                modifier = Modifier.padding(top = 12.dp, end = 4.dp),
                painter = painterResource(id = R.drawable.ic_priority_high_24dp),
                contentDescription = null,
                tint = Red
            )
        else
            Icon(
                modifier = Modifier.padding(top = 12.dp, end = 4.dp),
                painter = painterResource(id = R.drawable.ic_priority_low_24dp),
                contentDescription = null,
                tint = Gray
            )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun TasksItemText(
    isDone: Boolean,
    description: String,
    deadline: LocalDateTime?
) {
    val date = remember(deadline) { deadline?.toLocalDate()?.toStringDate() }
    val time = remember(deadline) {
        deadline?.toLocalTime()?.let {
            if (it.second == 0) null else it.toStringTime()
        }
    }

    AnimatedContent(
        targetState = isDone,
        label = "description animation"
    ) {
        Text(
            text = description,
            color = if (it) ExtendedTheme.colors.labelTertiary else ExtendedTheme.colors.labelPrimary,
            textDecoration = if (it) TextDecoration.LineThrough else TextDecoration.None,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            style = ExtendedTheme.typography.body
        )
    }
    Row {
        date?.let {
            Text(
                text = it,
                color = ExtendedTheme.colors.labelTertiary,
                style = ExtendedTheme.typography.subhead
            )
        }
        time?.let {
            Text(
                text = " / $it",
                color = ExtendedTheme.colors.labelTertiary,
                style = ExtendedTheme.typography.subhead
            )
        }
    }
}

@Composable
private fun TasksItemMenu(
    task: TodoItem,
    expanded: Boolean,
    hideMenu: () -> Unit,
    onAction: (TasksAction) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = hideMenu,
        modifier = Modifier
            .background(ExtendedTheme.colors.backElevated),
        offset = DpOffset(x = 16.dp, y = 0.dp)
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.edit)) },
            onClick = {
                onAction(TasksAction.EditTask(task))
                hideMenu()
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            },
            colors = MenuDefaults.itemColors(
                textColor = ExtendedTheme.colors.labelPrimary,
                leadingIconColor = ExtendedTheme.colors.labelPrimary
            )
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.delete)) },
            onClick = {
                onAction(TasksAction.DeleteTask(task))
                hideMenu()
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Delete,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection ?: return

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> GrayLight
            DismissValue.DismissedToEnd -> Green
            DismissValue.DismissedToStart -> Red
        }, label = "swipe animation"
    )

    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }
    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Done
        DismissDirection.EndToStart -> Icons.Default.Delete
    }
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f, label = "swipe scale animation"
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = "Localized description",
            modifier = Modifier.scale(scale),
            tint = White
        )
    }
}
