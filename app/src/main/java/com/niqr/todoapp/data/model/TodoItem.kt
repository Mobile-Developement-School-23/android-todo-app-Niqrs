package com.niqr.todoapp.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class TodoItem(
    val id: String,
    val description: String,
    val priority: Priority = Priority.COMMON,
    val deadline: LocalDate? = null,
    val isDone: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val editedAt: LocalDateTime? = null
)
