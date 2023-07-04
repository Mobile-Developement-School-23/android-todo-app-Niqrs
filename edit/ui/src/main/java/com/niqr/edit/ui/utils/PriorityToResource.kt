package com.niqr.edit.ui.utils

import com.niqr.edit.ui.R
import com.niqr.tasks.domain.model.Priority

fun Priority.toStringResource() = when(this) {
    Priority.COMMON -> R.string.priority_no
    Priority.LOW -> R.string.priority_low
    Priority.HIGH -> R.string.priority_high_extra
}