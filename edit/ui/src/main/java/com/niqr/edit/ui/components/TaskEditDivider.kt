package com.niqr.edit.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.niqr.core.ui.theme.ExtendedTheme

@Composable
fun TaskEditDivider(
    padding: PaddingValues
) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        color = ExtendedTheme.colors.supportOverlay
    )
}
