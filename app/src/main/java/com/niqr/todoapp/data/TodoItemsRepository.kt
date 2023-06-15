package com.niqr.todoapp.data

import com.niqr.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    fun todoItems(): Flow<List<TodoItem>>
    suspend fun addTodoItem(task: TodoItem)
    suspend fun deleteTodoItem(id: String)
}