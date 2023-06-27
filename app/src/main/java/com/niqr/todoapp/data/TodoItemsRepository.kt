package com.niqr.todoapp.data

import com.niqr.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    fun todoItems(): Flow<List<TodoItem>>
    fun doneVisible(): Flow<Boolean>
    fun doneCount(): Flow<Int>
    suspend fun findItemById(id: String): TodoItem?
    suspend fun addTodoItem(task: TodoItem)
    suspend fun updateTodoItem(task: TodoItem)
    suspend fun deleteTodoItem(task: TodoItem)
    suspend fun updateDoneTodoItemsVisibility(visible: Boolean)
}