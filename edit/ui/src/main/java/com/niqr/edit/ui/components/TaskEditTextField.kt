package com.niqr.edit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.TodoAppTheme
import com.niqr.edit.ui.R
import com.niqr.edit.ui.model.TaskEditAction

@Composable
fun TaskEditTextField(
    description: String,
    onAction: (TaskEditAction) -> Unit
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp),
        value = description,
        onValueChange = { onAction(TaskEditAction.DescriptionChange(it)) },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = ExtendedTheme.colors.labelPrimary
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        minLines = 3,
        cursorBrush = SolidColor(ExtendedTheme.colors.labelPrimary)
    ) { innerTextField ->

        Card(
            colors = CardDefaults.cardColors(
                containerColor = ExtendedTheme.colors.backSecondary,
                contentColor = ExtendedTheme.colors.labelTertiary
            )
        ) {

            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                if (description.isEmpty())
                    Text(
                        text = stringResource(id = R.string.task_input_hint),
                        style = ExtendedTheme.typography.body
                    )
                innerTextField.invoke()
            }
        }
    }
}

@Preview
@Composable
private fun TaskEditTextFieldPreview() {
    var description by remember {
        mutableStateOf("")
    }
    TodoAppTheme {
        Box(
            modifier = Modifier
                .background(ExtendedTheme.colors.backPrimary)
                .padding(16.dp)
        ) {
            TaskEditTextField(
                description = description,
                onAction = {
                    when(it) {
                        is TaskEditAction.DescriptionChange -> {
                            description = it.description
                        }
                        else -> Unit
                    }
                }
            )
        }
    }
}