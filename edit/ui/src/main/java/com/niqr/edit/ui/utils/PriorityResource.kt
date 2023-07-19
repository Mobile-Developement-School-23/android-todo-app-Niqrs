package com.niqr.edit.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.niqr.edit.ui.R
import com.niqr.tasks.domain.model.Priority

/**
 * Mapper for mapping Priority enum to string resource
 */
fun Priority.toStringResource() = when(this) {
    Priority.COMMON -> R.string.priority_no
    Priority.LOW -> R.string.priority_low
    Priority.HIGH -> R.string.priority_high_extra
}

@Composable
fun Priority.toText() = stringResource(when(this) {
    Priority.COMMON -> R.string.priority_no
    Priority.LOW -> R.string.priority_low
    Priority.HIGH -> R.string.priority_high
})
