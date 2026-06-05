package com.seijind.todo.todo.domain

import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.TodoDto
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.util.EmptyResult
import com.seijind.todo.util.Result
import com.seijind.todo.util.TodoError

interface TodoRepository {

    suspend fun all(): List<TodoDto>

    suspend fun get(id: String): Result<TodoDto, TodoError>

    suspend fun create(request: CreateTodoRequest): Result<TodoDto, TodoError>

    suspend fun update(id: String, request: UpdateTodoRequest): Result<TodoDto, TodoError>

    suspend fun delete(id: String): EmptyResult<TodoError>
}
