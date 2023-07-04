package com.niqr.todoapp.data.remote.model

import com.niqr.todoapp.data.model.TodoItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemsResponse(
    @SerialName("list") val tasks: List<TodoItem>,
    @SerialName("revision") val revision: String,
    @SerialName("status") val status: String
)