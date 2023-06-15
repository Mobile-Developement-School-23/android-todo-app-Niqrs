package com.niqr.todoapp.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class TodoItem(
    val id: String,
    val description: String,
    val priority: Priority = Priority.No,
    val date: LocalDate = LocalDate.now(),
    val isDone: Boolean = false,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val editedDate: LocalDateTime = LocalDateTime.now()
)
