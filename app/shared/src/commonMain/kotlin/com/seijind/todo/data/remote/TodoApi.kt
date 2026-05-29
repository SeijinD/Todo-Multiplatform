package com.seijind.todo.data.remote

import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.TodoDto
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.util.DataError
import com.seijind.todo.util.EmptyResult
import com.seijind.todo.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class TodoApi(
    private val client: HttpClient,
) {

    suspend fun getTodos(): Result<List<TodoDto>, DataError.Network> = safeCall {
        client.get("todos")
    }

    suspend fun getTodo(id: String): Result<TodoDto, DataError.Network> = safeCall {
        client.get("todos/$id")
    }

    suspend fun createTodo(request: CreateTodoRequest): Result<TodoDto, DataError.Network> = safeCall {
        client.post("todos") { setBody(request) }
    }

    suspend fun updateTodo(id: String, request: UpdateTodoRequest): Result<TodoDto, DataError.Network> = safeCall {
        client.put("todos/$id") { setBody(request) }
    }

    suspend fun deleteTodo(id: String): EmptyResult<DataError.Network> = safeCall {
        client.delete("todos/$id")
    }
}
