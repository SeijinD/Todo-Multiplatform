package com.seijind.todo.domain

import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.model.Todo
import com.seijind.todo.util.DataError
import com.seijind.todo.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun observeTodos(): Flow<List<Todo>>

    fun observeTodo(id: String): Flow<Todo?>

    suspend fun refresh(): EmptyResult<DataError>

    suspend fun create(request: CreateTodoRequest): EmptyResult<DataError>

    suspend fun update(id: String, request: UpdateTodoRequest): EmptyResult<DataError>

    suspend fun setCompleted(id: String, isCompleted: Boolean): EmptyResult<DataError>

    suspend fun delete(id: String): EmptyResult<DataError>
}
