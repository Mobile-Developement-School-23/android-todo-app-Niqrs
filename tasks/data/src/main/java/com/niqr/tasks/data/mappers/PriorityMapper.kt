package com.niqr.tasks.data.mappers

import com.niqr.tasks.domain.model.Priority

/**
 * Maps [Priority] enum to String
 */
fun Priority.mapToString() = when(this) {
    Priority.COMMON -> "basic"
    Priority.LOW -> "low"
    Priority.HIGH -> "important"
}

/**
 * Maps string to [Priority] enum
 */
fun priorityFromString(str: String) = when(str) {
    "important" -> Priority.HIGH
    "low" -> Priority.LOW
    else -> Priority.COMMON
}
