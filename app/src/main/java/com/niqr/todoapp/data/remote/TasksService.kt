package com.niqr.todoapp.data.remote

import com.niqr.todoapp.data.abstraction.AuthInfoProvider
import com.niqr.todoapp.data.model.AuthInfo
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.data.remote.model.RequestResult
import com.niqr.todoapp.data.remote.model.TodoItemRequest
import com.niqr.todoapp.data.remote.model.TodoItemResponse
import com.niqr.todoapp.data.remote.model.TodoItemsRequest
import com.niqr.todoapp.data.remote.model.TodoItemsResponse
import com.niqr.todoapp.data.remote.model.result
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
        client.get { requestHeaders() }.result()

    suspend fun mergeTasks(tasks: List<TodoItem>): RequestResult<TodoItemsResponse> =
        client.patch { requestHeaders(); setBody(TodoItemsRequest(tasks)) }.result()

    suspend fun addTask(task: TodoItem): RequestResult<TodoItemResponse> =
        client.post { requestHeaders(); setBody(TodoItemRequest(task)) }.result()

    suspend fun getTask(id: String): RequestResult<TodoItemResponse> =
        client.get(id) { requestHeaders() }.result()

    suspend fun updateTask(task: TodoItem): RequestResult<TodoItemResponse> =
        client.put(task.id) { requestHeaders(); setBody(TodoItemRequest(task)) }.result()

    suspend fun deleteTask(id: String): RequestResult<TodoItemResponse> =
        client.delete(id) { requestHeaders() }.result()


    private fun HttpMessageBuilder.requestHeaders() {
        headers {
            append(AUTHORIZATION, "$OAuth ${auth.value.token}")
            append(LAST_KNOWN_REVISION, auth.value.revision)
        }
    }
}