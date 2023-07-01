package com.niqr.todoapp.data.repo

import com.niqr.todoapp.data.abstraction.AuthInfoMutableProvider
import com.niqr.todoapp.data.abstraction.TodoItemsRepository
import com.niqr.todoapp.data.local.db.TaskDao
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.data.remote.TasksService
import com.niqr.todoapp.data.remote.model.RequestError
import com.niqr.todoapp.data.remote.model.RequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val authProvider: AuthInfoMutableProvider,
    private val service: TasksService,
    private val dao: TaskDao
): TodoItemsRepository {
    private val repoScope = CoroutineScope(Dispatchers.IO)
    private val deviceId by lazy { authProvider.authInfo().deviceId }
    private val recentlyDeleted = HashSet<String>()

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
        dao.insertTask(
            task.copy(
                lastUpdatedBy = deviceId
            )
        )
    }

    override suspend fun updateTodoItem(task: TodoItem) {
        dao.updateTask(
            task.copy(
                editedAt = LocalDateTime.now(),
                lastUpdatedBy = deviceId
            )
        )
    }

    override suspend fun deleteTodoItem(task: TodoItem) {
        recentlyDeleted.add(task.id)
        dao.deleteTask(task)
    }

    override suspend fun updateDoneTodoItemsVisibility(visible: Boolean) {
        isDoneVisibleFlow.update { visible }
    }

    override suspend fun updateTodoItems() {
        when(val result = service.getTasks()) {
            is RequestResult.Success -> {
                authProvider.updateRevision(result.value.revision)
                val newTasks = mergeTasks(tasksFLow.value, result.value.tasks)
                dao.replaceAll(newTasks)
                recentlyDeleted.clear()
            }
            is RequestResult.Error -> {
                //Should here really be something?
            }
        }
    }

    override suspend fun refreshTodoItems(): Boolean {
        when(val result = service.getTasks()) {
            is RequestResult.Error -> {
                if (result.e == RequestError.NO_CONNECTION)
                    return false
            }
            is RequestResult.Success -> {
                val newTasks = mergeRefreshedTasks(tasksFLow.value, result.value.tasks)
                dao.replaceAll(newTasks)
                repoScope.launch { service.mergeTasks(newTasks) }
                recentlyDeleted.clear()
            }
        }
        return true
    }

    override suspend fun pushTodoItems() {
        if (tasksFLow.value.isNotEmpty() || recentlyDeleted.isNotEmpty()) {
            service.mergeTasks(tasksFLow.value)
            recentlyDeleted.clear()
        }
    }

    override suspend fun clearTodoItems() {
        recentlyDeleted.clear()
        dao.clearAll()
    }

    private fun mergeTasks(old: List<TodoItem>, new: List<TodoItem>): List<TodoItem> {
        val newMapped = new.associateBy(TodoItem::id).toMutableMap()
        old.forEach { oldValue ->
            val newValue = newMapped[oldValue.id]
            if (newValue != null && oldValue.editedAt > newValue.editedAt)
                newMapped[newValue.id] = oldValue
        }
        return newMapped.values.toList()
    }

    private fun mergeRefreshedTasks(old: List<TodoItem>, new: List<TodoItem>): List<TodoItem> {
        val oldMapped = old.associateBy(TodoItem::id).toMutableMap()
        val diff = mutableListOf<TodoItem>()

        new.forEach { newValue ->
            val oldValue = oldMapped[newValue.id]

            if (oldValue != null) {
                if (newValue.editedAt > oldValue.editedAt)
                    oldMapped[newValue.id] = newValue
            } else {
                diff.add(newValue)
            }
        }

        val newTasks = diff.filter { !recentlyDeleted.contains(it.id) }

        return oldMapped.values + newTasks
    }
}