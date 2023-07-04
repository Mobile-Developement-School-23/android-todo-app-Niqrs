package com.niqr.tasks.data

import com.niqr.auth.domain.AuthInfoMutableProvider
import com.niqr.tasks.data.local.db.TaskDao
import com.niqr.tasks.data.mappers.fromDto
import com.niqr.tasks.data.mappers.toDto
import com.niqr.tasks.data.model.TodoItemDto
import com.niqr.tasks.data.remote.TasksService
import com.niqr.tasks.data.remote.model.RequestError
import com.niqr.tasks.data.remote.model.RequestResult
import com.niqr.tasks.domain.model.TodoItem
import com.niqr.tasks.domain.repo.TodoItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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
        tasksFLow.map { it.map(TodoItemDto::fromDto) }

    override fun doneVisible(): Flow<Boolean> =
        isDoneVisibleFlow.asStateFlow()

    override fun doneCount(): Flow<Int> =
        dao.getDoneCount()

    override suspend fun findItemById(id: String): TodoItem? {
        return dao.findTaskById(id)?.fromDto()
    }

    override suspend fun addTodoItem(task: TodoItem) {
        dao.insertTask(
            task.copy(
                lastUpdatedBy = deviceId
            ).toDto()
        )
    }

    override suspend fun updateTodoItem(task: TodoItem) {
        dao.updateTask(
            task.copy(
                editedAt = LocalDateTime.now(),
                lastUpdatedBy = deviceId
            ).toDto()
        )
    }

    override suspend fun deleteTodoItem(task: TodoItem) {
        recentlyDeleted.add(task.id)
        dao.deleteTask(task.toDto())
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

    private fun mergeTasks(old: List<TodoItemDto>, new: List<TodoItemDto>): List<TodoItemDto> {
        val newMapped = new.associateBy(TodoItemDto::id).toMutableMap()
        old.forEach { oldValue ->
            val newValue = newMapped[oldValue.id]
            if (newValue != null && oldValue.editedAt > newValue.editedAt)
                newMapped[newValue.id] = oldValue
        }
        return newMapped.values.toList()
    }

    private fun mergeRefreshedTasks(old: List<TodoItemDto>, new: List<TodoItemDto>): List<TodoItemDto> {
        val oldMapped = old.associateBy(TodoItemDto::id).toMutableMap()
        val diff = mutableListOf<TodoItemDto>()

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