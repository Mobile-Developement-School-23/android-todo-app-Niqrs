package com.niqr.edit.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.niqr.core.ui.theme.Blue
import com.niqr.core.ui.theme.BlueTranslucent
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.TodoAppTheme
import com.niqr.edit.ui.R
import com.niqr.edit.ui.model.TaskEditAction
import com.niqr.edit.ui.utils.toLong
import com.niqr.edit.ui.utils.toStringDate
import com.niqr.edit.ui.utils.toStringTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun TaskEditTimeField(
    time: LocalDateTime,
    isDateVisible: Boolean,
    onAction: (TaskEditAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 16.dp)
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val dateText = remember(time) { time.toLocalDate().toStringDate() }
        val timeText = remember(time) { time.toLocalTime().toStringTime() }
        var openDateDialog by remember { mutableStateOf(false) }
        var openTimeDialog by remember { mutableStateOf(false) }

        DatePicker(
            date = time.toLocalDate(),
            open = openDateDialog,
            closePicker = { openDateDialog = false },
            onAction = onAction
        )

        TimePicker(
            time = time.toLocalTime(),
            open = openTimeDialog,
            closePicker = { openTimeDialog = false },
            onAction = onAction
        )

        Column {
            Text(
                text = stringResource(id = R.string.do_it_till),
                modifier = Modifier.padding(start = 4.dp),
                color = ExtendedTheme.colors.labelPrimary,
                style = ExtendedTheme.typography.body
            )

            AnimatedVisibility(visible = isDateVisible) {
                Row {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { openDateDialog = true }
                            .padding(4.dp)
                    ) {
                        Text(
                            text = dateText,
                            color = Blue,
                            style = ExtendedTheme.typography.subhead
                        )
                    }

                    Text(
                        text = "/",
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 1.dp),
                        color = ExtendedTheme.colors.labelTertiary,
                        style = ExtendedTheme.typography.subhead
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { openTimeDialog = true }
                            .padding(4.dp)
                    ) {
                        if (time.second == 0) {
                            Text(
                                text = stringResource(R.string.select_time),
                                color = ExtendedTheme.colors.labelTertiary,
                                style = ExtendedTheme.typography.subhead
                            )
                        } else {
                            Text(
                                text = timeText,
                                color = Blue,
                                style = ExtendedTheme.typography.subhead
                            )
                        }
                    }
                }
            }
        }

        Switch(
            checked = isDateVisible,
            onCheckedChange = { onAction(TaskEditAction.UpdateDeadlineVisibility(!isDateVisible)) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Blue,
                checkedTrackColor = BlueTranslucent,
                uncheckedThumbColor = ExtendedTheme.colors.backElevated,
                uncheckedTrackColor = ExtendedTheme.colors.supportOverlay,
                uncheckedBorderColor = ExtendedTheme.colors.supportOverlay,
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePicker(
    date: LocalDate,
    open: Boolean,
    closePicker: () -> Unit,
    onAction: (TaskEditAction) -> Unit
) {
    if (open) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date.toLong())
        val confirmEnabled by remember(datePickerState.selectedDateMillis) {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            onDismissRequest = closePicker,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onAction(TaskEditAction.UpdateDate(it))
                        }
                        closePicker()
                    },
                    enabled = confirmEnabled
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = closePicker
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePicker(
    time: LocalTime,
    open: Boolean,
    closePicker: () -> Unit,
    onAction: (TaskEditAction) -> Unit
) {
    if (open) {
        val timePickerState = rememberTimePickerState(time.hour, time.minute)

        TimePickerDialog(
            title = stringResource(R.string.select_time_title),
            onDismiss = closePicker,
            onRemove = {
                timePickerState.let {
                    onAction(TaskEditAction.UpdateTime(it.hour, it.minute, 0))
                }
                closePicker()
            },
            onConfirm = {
                timePickerState.let {
                    onAction(TaskEditAction.UpdateTime(it.hour, it.minute, 1))
                }
                closePicker()
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun TimePickerDialog(
    title: String,
    onDismiss: () -> Unit,
    onRemove: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            toggle()
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelLarge
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onRemove
                    ) { Text(stringResource(R.string.remove)) }
                    TextButton(
                        onClick = onConfirm
                    ) { Text(stringResource(R.string.ok)) }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskEditTimeFieldPreview() {
    var isDateVisible by remember {
        mutableStateOf(true)
    }
    TodoAppTheme {
        Box(
            modifier = Modifier
                .background(ExtendedTheme.colors.backPrimary)
                .padding(16.dp)
        ) {
            TaskEditTimeField(
                time = LocalDateTime.now(),
                isDateVisible = isDateVisible,
                onAction = {
                    when(it) {
                        is TaskEditAction.UpdateDeadlineVisibility -> {
                            isDateVisible = it.visible
                        }
                        else -> Unit
                    }
                }
            )
        }
    }
}