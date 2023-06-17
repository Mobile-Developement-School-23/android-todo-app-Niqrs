package com.niqr.todoapp.data

import com.niqr.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    fun todoItems(): Flow<List<TodoItem>>
    suspend fun findItemById(id: String): TodoItem?
    suspend fun addTodoItem(task: TodoItem)
    suspend fun updateTodoItem(task: TodoItem)
    suspend fun deleteTodoItemAt(position: Int)
    suspend fun deleteTodoItem(id: String)
}