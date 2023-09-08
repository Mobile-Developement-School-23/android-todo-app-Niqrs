package com.niqr.tasks.domain.model

import java.time.LocalDateTime
import java.util.UUID

/**
 * Main [TodoItem] model
 *
 *
 * Represents all information about Task
 */
data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val deadline: LocalDateTime? = null,
    val priority: Priority = Priority.COMMON,
    val isDone: Boolean = false,
    val color: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val editedAt: LocalDateTime = LocalDateTime.now(),
    val lastUpdatedBy: String = "1110111"
)
