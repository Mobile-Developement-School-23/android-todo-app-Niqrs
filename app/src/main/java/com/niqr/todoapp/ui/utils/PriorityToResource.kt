package com.niqr.todoapp.ui.utils

import androidx.core.content.res.ResourcesCompat
import com.niqr.todoapp.R
import com.niqr.todoapp.data.model.Priority

fun Priority.toResource() = when(this) {
    Priority.No -> ResourcesCompat.ID_NULL
    Priority.Low -> R.drawable.ic_priority_low_24dp
    Priority.High -> R.drawable.ic_priority_high_24dp
}