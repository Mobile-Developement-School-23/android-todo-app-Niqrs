package com.niqr.tasks.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val deadline: LocalDate? = null,
    val priority: Priority = Priority.COMMON,
    val isDone: Boolean = false,
    val color: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val editedAt: LocalDateTime = LocalDateTime.now(),
    val lastUpdatedBy: String = "1110111"
)