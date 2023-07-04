package com.niqr.todoapp.utils

import com.niqr.tasks.domain.model.Priority
import com.niqr.todoapp.R

fun Priority.toStringResource() = when(this) {
    Priority.COMMON -> R.string.priority_no
    Priority.LOW -> R.string.priority_low
    Priority.HIGH -> R.string.priority_high_extra
}