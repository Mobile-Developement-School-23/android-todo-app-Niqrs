package com.niqr.tasks.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.niqr.core.ui.theme.Red
import com.niqr.tasks.ui.R
import com.niqr.tasks.ui.model.TasksAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheetContent(
    onAction: (TasksAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomSheetDefaults.DragHandle()

        OutlinedButton(
            onClick = { onAction(TasksAction.SignOut) },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Red
            ),
            contentPadding = PaddingValues(12.dp)
        ) {
            Text(text = stringResource(R.string.sign_out))
        }
    }
}