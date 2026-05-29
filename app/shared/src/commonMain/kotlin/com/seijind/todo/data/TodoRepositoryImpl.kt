package com.seijind.todo.data

import com.seijind.todo.data.local.TodoDao
import com.seijind.todo.data.mapper.toDomain
import com.seijind.todo.data.mapper.toEntity
import com.seijind.todo.data.remote.TodoApi
import com.seijind.todo.domain.TodoRepository
import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.model.Todo
import com.seijind.todo.util.AppDispatchers
import com.seijind.todo.util.DataError
import com.seijind.todo.util.EmptyResult
import com.seijind.todo.util.Result
import com.seijind.todo.util.asEmptyResult
import com.seijind.todo.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TodoRepositoryImpl(
    private val api: TodoApi,
    private val dao: TodoDao,
    private val dispatchers: AppDispatchers,
) : TodoRepository {

    override fun observeTodos(): Flow<List<Todo>> =
        dao.observeTodos().map { list -> list.map { it.toDomain() } }

    override fun observeTodo(id: String): Flow<Todo?> =
        dao.observeTodoById(id).map { it?.toDomain() }

    override suspend fun refresh(): EmptyResult<DataError> = withContext(dispatchers.io) {
        api.getTodos()
            .onSuccess { dtos -> dao.upsertTodos(dtos.map { it.toEntity() }) }
            .asEmptyResult()
    }

    override suspend fun create(request: CreateTodoRequest): EmptyResult<DataError> =
        withContext(dispatchers.io) {
            api.createTodo(request)
                .onSuccess { dao.upsertTodo(it.toEntity()) }
                .asEmptyResult()
        }

    override suspend fun update(id: String, request: UpdateTodoRequest): EmptyResult<DataError> =
        withContext(dispatchers.io) {
            api.updateTodo(id, request)
                .onSuccess { dao.upsertTodo(it.toEntity()) }
                .asEmptyResult()
        }

    override suspend fun setCompleted(id: String, isCompleted: Boolean): EmptyResult<DataError> =
        update(id, UpdateTodoRequest(isCompleted = isCompleted))

    override suspend fun delete(id: String): EmptyResult<DataError> =
        withContext(dispatchers.io) {
            when (val result = api.deleteTodo(id)) {
                is Result.Success -> {
                    dao.deleteTodoById(id)
                    Result.Success(Unit)
                }
                is Result.Error -> result
            }
        }
}
