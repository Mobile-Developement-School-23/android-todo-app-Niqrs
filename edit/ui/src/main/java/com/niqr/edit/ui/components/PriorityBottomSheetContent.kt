package com.niqr.edit.ui.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.components.MultiSelector
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.Red
import com.niqr.edit.ui.R
import com.niqr.edit.ui.model.TaskEditAction
import com.niqr.edit.ui.utils.toText
import com.niqr.tasks.domain.model.Priority
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PriorityBottomSheetContent(
    priority: Priority,
    onAction: (TaskEditAction) -> Unit
) {
    val scope = rememberCoroutineScope()
    val priorityOptions = listOf(
        stringResource(R.string.priority_low),
        stringResource(R.string.priority_no),
        stringResource(R.string.priority_high)
    )

    Text(
        text = stringResource(R.string.priority),
        color = ExtendedTheme.colors.labelPrimary,
        style = ExtendedTheme.typography.titleSmall
    )

    Spacer(modifier = Modifier.height(12.dp))

    val border = BorderStroke(1.dp, ExtendedTheme.colors.supportSeparator)
    val labelReversedColor = ExtendedTheme.colors.labelPrimaryReversed
    val selectedColor = remember { Animatable(labelReversedColor) }

    MultiSelector(
        options = priorityOptions,
        selectedOption = priority.toText(),
        onOptionSelect = {
            val newPriority = when(it) {
                priorityOptions[0] -> Priority.LOW
                priorityOptions[2] -> Priority.HIGH
                else -> Priority.COMMON
            }

            if (newPriority != priority) {
                scope.coroutineContext.cancelChildren()
                scope.launch {
                    if (newPriority == Priority.HIGH) {
                        delay(350)
                        selectedColor.animateTo(Red, repeatable(3, tween(300), RepeatMode.Reverse))
                    }
                    selectedColor.animateTo(labelReversedColor, tween(300))
                }

                onAction(TaskEditAction.UpdatePriority(newPriority))
            }
        },
        modifier = Modifier
            .border(border, CircleShape)
            .fillMaxWidth(0.9f),
        selectedColor = selectedColor.value
    )

    Spacer(modifier = Modifier.height(6.dp))
}