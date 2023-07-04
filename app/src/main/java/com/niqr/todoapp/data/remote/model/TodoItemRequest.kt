package com.niqr.todoapp.data.remote.model

import com.niqr.todoapp.data.model.TodoItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemRequest(
    @SerialName("element") val element: TodoItem
)