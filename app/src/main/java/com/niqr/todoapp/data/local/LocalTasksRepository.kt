package com.niqr.todoapp.data.local

import com.niqr.todoapp.data.TodoItemsRepository
import com.niqr.todoapp.data.model.Priority
import com.niqr.todoapp.data.model.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocalTasksRepository: TodoItemsRepository {
    private val tasks = sampleTasks.toMutableList()

    val repoScope = CoroutineScope(Dispatchers.IO) //This repo is not really async, but imagine it is
    private val tasksFlow = MutableStateFlow(tasks)

    override fun todoItems(): Flow<List<TodoItem>> =
        tasksFlow

    override suspend fun addTodoItem(task: TodoItem) {
        repoScope.launch { //This function is not really async, but imagine it is
            tasks.add(task)
            tasksFlow.update {
                tasks
            }
        }
    }

    override suspend fun deleteTodoItem(id: String) {
        repoScope.launch { //This function is not really async, but imagine it is
            val task = tasks.find { it.id == id } ?: return@launch
            tasks.remove(task)
        }
    }
}

private val sampleTasks = listOf(
    TodoItem(
        id = "1",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.Low
    ),
    TodoItem(
        id = "2",
        description = "WAKE UP!",
        priority = Priority.High
    ),
    TodoItem(
        id = "3",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.Low
    ),TodoItem(
        id = "4",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.High
    ),
    TodoItem(
        id = "5",
        description = "Normal looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.No
    ),
    TodoItem(
        id = "6",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.Low
    ),
    TodoItem(
        id = "7",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.High
    ),
    TodoItem(
        id = "8",
        description = "Simple looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.Low
    ),
    TodoItem(
        id = "9",
        description = "Normal task of Yandex Senior Android developer",
        priority = Priority.No
    ),TodoItem(
        id = "10",
        description = "Normal task of Yandex Senior Android developer",
        priority = Priority.No
    ),
    TodoItem(
        id = "11",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.Low
    ),
    TodoItem(
        id = "12",
        description = "Hard looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.High
    ),
    TodoItem(
        id = "13",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.Low
    ),
    TodoItem(
        id = "14",
        description = "Normal task of Yandex Senior Android developer",
        priority = Priority.No
    ),
    TodoItem(
        id = "15",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.High
    ),
    TodoItem(
        id = "16",
        description = "Simple task of Yandex Senior Android developer",
        priority = Priority.Low
    ),
    TodoItem(
        id = "17",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.High
    ),
    TodoItem(
        id = "18",
        description = "Hard task of Yandex Senior Android developer",
        priority = Priority.High
    ),
    TodoItem(
        id = "19",
        description = "Hard looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.High
    ),
    TodoItem(
        id = "20",
        description = "Normal looooooooooooooooooooooooooooooooooooooooooooooooooong task of Yandex Senior Android developer",
        priority = Priority.No
    ),
    TodoItem(
        id = "21",
        description = "Normal task of Yandex Senior Android developer",
        priority = Priority.No
    ),
)