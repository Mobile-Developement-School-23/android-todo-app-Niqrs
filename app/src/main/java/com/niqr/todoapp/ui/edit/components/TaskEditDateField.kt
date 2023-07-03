package com.niqr.todoapp.ui.edit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.unit.dp
import com.niqr.todoapp.R
import com.niqr.todoapp.ui.edit.model.TaskEditAction
import com.niqr.todoapp.ui.theme.Blue
import com.niqr.todoapp.ui.theme.BlueTranslucent
import com.niqr.todoapp.ui.theme.ExtendedTheme
import com.niqr.todoapp.utils.toLong
import com.niqr.todoapp.utils.toStringDate
import java.time.LocalDate

@Composable
fun TaskEditDateField(
    date: LocalDate,
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
        val dateText = remember(date) { date.toStringDate() }
        var openDialog by remember { mutableStateOf(false) }

        DatePicker(
            date = date,
            open = openDialog,
            closePicker = { openDialog = false },
            onAction = onAction
        )

        Column {
            Text(
                text = stringResource(id = R.string.do_it_till),
                modifier = Modifier.padding(start = 4.dp),
                color = ExtendedTheme.colors.labelPrimary
            )

            AnimatedVisibility(visible = isDateVisible) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { openDialog = true }
                        .padding(4.dp)
                ) {
                    Text(text = dateText, color = Blue)
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
                            onAction(TaskEditAction.UpdateDeadline(it))
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