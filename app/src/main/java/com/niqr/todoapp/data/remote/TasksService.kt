package com.niqr.todoapp.data.remote

import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.data.remote.model.RequestResult
import com.niqr.todoapp.data.remote.model.TodoItemRequest
import com.niqr.todoapp.data.remote.model.TodoItemResponse
import com.niqr.todoapp.data.remote.model.TodoItemsRequest
import com.niqr.todoapp.data.remote.model.TodoItemsResponse
import com.niqr.todoapp.data.remote.model.result
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpMessageBuilder
import javax.inject.Inject

class TasksService @Inject constructor(
    private val client: HttpClient
) {
    private val rev = "26"

    suspend fun getTasks(): RequestResult<TodoItemsResponse> =
        client.get { requestHeaders(rev) }.result()

    suspend fun mergeTasks(tasks: List<TodoItem>): RequestResult<TodoItemsResponse> =
        client.patch { requestHeaders(rev); setBody(TodoItemsRequest(tasks)) }.result()

    suspend fun addTask(task: TodoItem): RequestResult<TodoItemResponse> =
        client.post { requestHeaders(rev); setBody(TodoItemRequest(task)) }.result()

    suspend fun getTask(id: String): RequestResult<TodoItemResponse> =
        client.get(id) { requestHeaders(rev) }.result()

    suspend fun updateTask(task: TodoItem): RequestResult<TodoItemResponse> =
        client.put(task.id) { requestHeaders(rev); setBody(TodoItemRequest(task)) }.result()

    suspend fun deleteTask(id: String): RequestResult<TodoItemResponse> =
        client.delete(id) { requestHeaders(rev) }.result()


    private fun HttpMessageBuilder.requestHeaders(revision: String) {
        headers {
//            append(AUTHORIZATION, "$OAuth $OAuthToken")
//            append(LAST_KNOWN_REVISION, revision)
        }
    }
}