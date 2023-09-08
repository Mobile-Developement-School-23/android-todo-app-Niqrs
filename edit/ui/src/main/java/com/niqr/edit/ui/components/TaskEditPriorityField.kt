package com.niqr.edit.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.Red
import com.niqr.core.ui.theme.TodoAppTheme
import com.niqr.edit.ui.R
import com.niqr.edit.ui.model.TaskEditAction
import com.niqr.edit.ui.utils.toStringResource
import com.niqr.tasks.domain.model.Priority

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TaskEditPriorityField(
    priority: Priority,
    onAction: (TaskEditAction) -> Unit
) {
    val isHighPriority = remember(priority) { priority == Priority.HIGH }

    Column(
        modifier = Modifier
            .padding(top = 24.dp, bottom = 12.dp)
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(6.dp))
            .clickable {
                onAction(TaskEditAction.PriorityChoose)
            }
            .padding(4.dp)
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides ExtendedTheme.typography.body
        ) {
            Text(
                text = stringResource(id = R.string.priority),
                color = ExtendedTheme.colors.labelPrimary
            )

            AnimatedContent(
                targetState = priority,
                label = "Priority change animation"
            ) {
                Text(
                    text = stringResource(id = it.toStringResource()),
                    modifier = Modifier.padding(top = 2.dp),
                    color = if (isHighPriority) Red else ExtendedTheme.colors.labelTertiary,
                    style = ExtendedTheme.typography.subhead
                )
            }
        }
    }
}

@Preview
@Composable
private fun TaskEditPriorityFieldPreview() {
    TodoAppTheme {
        Box(
            modifier = Modifier
                .background(ExtendedTheme.colors.backPrimary)
                .padding(4.dp)
        ) {
            TaskEditPriorityField(
                priority = Priority.HIGH,
                onAction = {}
            )
        }
    }
}