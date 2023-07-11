package com.niqr.tasks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.theme.Blue
import com.niqr.core.ui.theme.ExtendedTheme
import com.niqr.core.ui.theme.Red
import com.niqr.tasks.ui.R
import com.niqr.tasks.ui.model.TasksAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheetContent(
    onAction: (TasksAction) -> Unit
) {
    val defaultSelected = stringResource(R.string.theme_system)
    var selected by remember{ mutableStateOf(defaultSelected) }
    val border = BorderStroke(1.dp, ExtendedTheme.colors.supportSeparator)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomSheetDefaults.DragHandle()

        Text(text = stringResource(R.string.theme))

        Spacer(modifier = Modifier.height(12.dp))

        MultiSelector(
            options = listOf(stringResource(R.string.theme_light), stringResource(R.string.theme_system), stringResource(R.string.theme_dark)),
            selectedOption = selected,
            onOptionSelect = { selected = it },
            modifier = Modifier
                .border(border, CircleShape)
                .heightIn(48.dp, 64.dp)
                .fillMaxWidth(0.9f),
            selectionColor = Blue,
            selectedColor = ExtendedTheme.colors.labelPrimaryReversed,
            unselectedColor = ExtendedTheme.colors.labelPrimary
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
            Text(text = stringResource(R.string.sign_out))
        }
    }
}