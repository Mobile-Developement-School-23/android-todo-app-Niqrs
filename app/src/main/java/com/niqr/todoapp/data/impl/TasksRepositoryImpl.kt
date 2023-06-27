package com.niqr.todoapp.data.impl

import com.niqr.todoapp.data.TodoItemsRepository
import com.niqr.todoapp.data.local.TaskDao
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.data.remote.TasksService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val service: TasksService,
    private val dao: TaskDao
): TodoItemsRepository {
    private val repoScope = CoroutineScope(Dispatchers.Main)

    private var isDoneVisibleFlow = MutableStateFlow(true)

    private val tasksFLow = dao.getAllTasks().stateIn(
        scope = repoScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    override fun todoItems(): Flow<List<TodoItem>> =
        tasksFLow

    override fun doneVisible(): Flow<Boolean> =
        isDoneVisibleFlow.asStateFlow()

    override fun doneCount(): Flow<Int> =
        dao.getDoneCount()

    override suspend fun findItemById(id: String): TodoItem? {
        return dao.findTaskById(id)
    }

    override suspend fun addTodoItem(task: TodoItem) {
        dao.insertTask(task)
    }

    override suspend fun updateTodoItem(task: TodoItem) {
        dao.updateTask(task)
    }

    override suspend fun deleteTodoItem(task: TodoItem) {
        dao.deleteTask(task)
    }

    override suspend fun updateDoneTodoItemsVisibility(visible: Boolean) {
        isDoneVisibleFlow.update { visible }
    }
}