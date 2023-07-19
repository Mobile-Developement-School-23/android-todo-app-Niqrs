package com.niqr.tasks.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.niqr.settings.domain.model.Theme
import com.niqr.tasks.ui.R

@Composable
fun Theme.toText() = stringResource(when(this) {
    Theme.LIGHT -> R.string.theme_light
    Theme.DARK -> R.string.theme_dark
    Theme.SYSTEM -> R.string.theme_system
})