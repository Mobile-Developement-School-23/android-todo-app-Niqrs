package com.niqr.todoapp.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.niqr.settings.domain.model.Theme

@Composable
fun Theme.isDarkTheme() = when(this) {
    Theme.LIGHT -> false
    Theme.DARK -> true
    Theme.SYSTEM -> isSystemInDarkTheme()
}