package com.niqr.tasks.data.mappers

import com.niqr.tasks.domain.model.Priority

fun Priority.mapToString() = when(this) {
    Priority.COMMON -> "basic"
    Priority.LOW -> "low"
    Priority.HIGH -> "important"
}

fun priorityFromString(str: String) = when(str) {
    "important" -> Priority.HIGH
    "low" -> Priority.LOW
    else -> Priority.COMMON
}