package com.niqr.todoapp.data.remote

import com.niqr.todoapp.data.abstraction.AuthInfoProvider
import com.niqr.todoapp.data.model.AuthInfo
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.data.remote.model.RequestResult
import com.niqr.todoapp.data.remote.model.TodoItemRequest
import com.niqr.todoapp.data.remote.model.TodoItemResponse
import com.niqr.todoapp.data.remote.model.TodoItemsRequest
import com.niqr.todoapp.data.remote.model.TodoItemsResponse
import com.niqr.todoapp.data.remote.model.safeRequest
import com.niqr.todoapp.utils.AUTHORIZATION
import com.niqr.todoapp.utils.LAST_KNOWN_REVISION
import com.niqr.todoapp.utils.OAuth
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpMessageBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class TasksService @Inject constructor(
    private val client: HttpClient,
    authProvider: AuthInfoProvider
) {
    private val auth = authProvider.authInfoFlow().stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.Eagerly,
        AuthInfo()
    )

    suspend fun getTasks(): RequestResult<TodoItemsResponse> =
        client.safeRequest { get { requestHeaders() } }

    suspend fun mergeTasks(tasks: List<TodoItem>): RequestResult<TodoItemsResponse> =
        client.safeRequest { patch { requestHeaders(); setBody(TodoItemsRequest(tasks)) } }

    suspend fun addTask(task: TodoItem): RequestResult<TodoItemResponse> =
        client.safeRequest { post { requestHeaders(); setBody(TodoItemRequest(task)) } }

    suspend fun getTask(id: String): RequestResult<TodoItemResponse> =
        client.safeRequest { get(id) { requestHeaders() } }

    suspend fun updateTask(task: TodoItem): RequestResult<TodoItemResponse> =
        client.safeRequest { put(task.id) { requestHeaders(); setBody(TodoItemRequest(task)) } }

    suspend fun deleteTask(id: String): RequestResult<TodoItemResponse> =
        client.safeRequest { delete(id) { requestHeaders() } }


    private fun HttpMessageBuilder.requestHeaders() {
        headers {
            append(AUTHORIZATION, "$OAuth ${auth.value.token}")
            append(LAST_KNOWN_REVISION, auth.value.revision)
        }
    }
}