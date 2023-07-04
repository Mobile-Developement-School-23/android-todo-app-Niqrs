package com.niqr.tasks.data.remote.model

import com.niqr.tasks.data.model.TodoItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemRequest(
    @SerialName("element") val element: TodoItemDto
)