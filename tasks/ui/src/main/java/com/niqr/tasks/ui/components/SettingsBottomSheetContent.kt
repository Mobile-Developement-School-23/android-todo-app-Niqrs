package com.niqr.tasks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.components.MultiSelector
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.Red
import com.niqr.settings.domain.model.Theme
import com.niqr.tasks.ui.R
import com.niqr.tasks.ui.model.TasksAction
import com.niqr.tasks.ui.utils.toText

@Composable
fun ColumnScope.SettingsBottomSheetContent(
    appTheme: Theme,
    onAction: (TasksAction) -> Unit
) {
    val border = BorderStroke(1.dp, ExtendedTheme.colors.supportSeparator)
    val themeOptions = listOf(
        stringResource(R.string.theme_light),
        stringResource(R.string.theme_system),
        stringResource(R.string.theme_dark)
    )

    Text(
        text = stringResource(R.string.theme),
        color = ExtendedTheme.colors.labelPrimary,
        style = ExtendedTheme.typography.titleSmall
    )

    Spacer(modifier = Modifier.height(12.dp))

    MultiSelector(
        options = themeOptions,
        selectedOption = appTheme.toText(),
        onOptionSelect = {
            val newTheme = when(it) {
                themeOptions[0] -> Theme.LIGHT
                themeOptions[2] -> Theme.DARK
                else -> Theme.SYSTEM
            }

            onAction(TasksAction.UpdateTheme(newTheme))
        },
        modifier = Modifier
            .border(border, CircleShape)
            .fillMaxWidth(0.9f)
    )

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        color = ExtendedTheme.colors.supportSeparator
    )

    OutlinedButton(
        onClick = { onAction(TasksAction.SignOut) },
        modifier = Modifier
            .heightIn(48.dp, 64.dp)
            .fillMaxWidth(0.9f),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Red
        ),
        border = border
    ) {
        Text(
            text = stringResource(R.string.sign_out),
            style = ExtendedTheme.typography.button
        )
    }
}