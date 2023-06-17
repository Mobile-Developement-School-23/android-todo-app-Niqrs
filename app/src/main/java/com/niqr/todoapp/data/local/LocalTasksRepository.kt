package com.niqr.todoapp.data.local

import android.util.Log
import com.niqr.todoapp.data.TodoItemsRepository
import com.niqr.todoapp.data.model.Priority
import com.niqr.todoapp.data.model.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class LocalTasksRepository @Inject constructor(): TodoItemsRepository {
    private val tasks: MutableList<TodoItem> = sampleTasks.toMutableList()

    private val tasksFlow = MutableStateFlow(tasks.toList())

    override fun todoItems(): Flow<List<TodoItem>> =
        tasksFlow.asStateFlow()

    override suspend fun findItemById(id: String): TodoItem? {
        return tasks.firstOrNull { it.id == id }
    }

    override suspend fun addTodoItem(task: TodoItem) {
        tasks.add(task)
        updateFlow()
    }

    override suspend fun updateTodoItem(task: TodoItem) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index == -1)
            return
        tasks[index] = task
        updateFlow()
    }

    override suspend fun deleteTodoItemAt(position: Int) {
        tasks.removeAt(position)
        updateFlow()
    }

    override suspend fun deleteTodoItem(id: String) {
        val task = tasks.find { it.id == id } ?: return
        tasks.remove(task)
        updateFlow()
    }

    private fun updateFlow() {
        tasksFlow.update {
            tasks.map { it.copy() }.toList()
        }
    }
}

private val sampleTasks = listOf(
    TodoItem(
        id = "1",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.LOW,
        isDone = true,
        editedAt = LocalDateTime.now().minusDays(3)
    ),
    TodoItem(
        id = "2",
        description = "WAKE UP!",
        priority = Priority.HIGH,
        deadline = LocalDate.now().plusDays(5),
        isDone = true
    ),
    TodoItem(
        id = "3",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.COMMON
    ),TodoItem(
        id = "4",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.HIGH,
        isDone = true,
        editedAt = LocalDateTime.now().minusDays(1)
    ),
    TodoItem(
        id = "5",
        description = "Normal looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.COMMON,
        deadline = LocalDate.now().plusDays(56)
    ),
    TodoItem(
        id = "6",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.LOW
    ),
    TodoItem(
        id = "7",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.HIGH
    ),
    TodoItem(
        id = "8",
        description = "Simple looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.LOW,
        isDone = true
    ),
    TodoItem(
        id = "9",
        description = "Normal task of Yandex Senior Android developer",
        priority = Priority.COMMON,
        deadline = LocalDate.now().plusDays(1)
    ),TodoItem(
        id = "10",
        description = "Normal task of Yandex Senior Android developer",
        priority = Priority.COMMON
    ),
    TodoItem(
        id = "11",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.LOW
    ),
    TodoItem(
        id = "12",
        description = "Hard looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.HIGH
    ),
    TodoItem(
        id = "13",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.LOW,
        isDone = true
    ),
    TodoItem(
        id = "14",
        description = "Normal task of Yandex Senior Android developer",
        priority = Priority.COMMON,
        editedAt = LocalDateTime.now().minusHours(6)
    ),
    TodoItem(
        id = "15",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.HIGH,
        deadline = LocalDate.now().plusDays(370)
    ),
    TodoItem(
        id = "16",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.LOW,
        isDone = true
    ),
    TodoItem(
        id = "17",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.HIGH
    ),
    TodoItem(
        id = "18",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.HIGH
    ),
    TodoItem(
        id = "19",
        description = "Hard looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.HIGH,
        isDone = true
    ),
    TodoItem(
        id = "20",
        description = "Normal looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.COMMON
    ),
    TodoItem(
        id = "21",
        description = "Normal task of Yandex Senior Android developer",
        priority = Priority.COMMON
    ),
)