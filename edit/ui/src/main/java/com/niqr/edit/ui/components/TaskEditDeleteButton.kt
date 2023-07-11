package com.niqr.edit.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.Red
import com.niqr.edit.ui.R
import com.niqr.edit.ui.model.TaskEditAction

@Composable
fun TaskEditDeleteButton(
    enabled: Boolean,
    onAction: (TaskEditAction) -> Unit
) {
    val deleteButtonColor by animateColorAsState(
        targetValue = if (enabled) Red else ExtendedTheme.colors.labelDisable,
        label = "button color animation"
    )

    TextButton(
        onClick = { onAction(TaskEditAction.DeleteTask) },
        modifier = Modifier.padding(horizontal = 4.dp),
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = deleteButtonColor,
            disabledContentColor = deleteButtonColor
        )
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete),
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            text = stringResource(id = R.string.delete),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
