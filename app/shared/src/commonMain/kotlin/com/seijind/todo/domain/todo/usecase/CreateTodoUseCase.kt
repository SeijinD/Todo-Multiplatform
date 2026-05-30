package com.seijind.todo.domain.todo.usecase

import com.seijind.todo.domain.todo.TodoRepository
import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.util.DataError
import com.seijind.todo.util.EmptyResult

class CreateTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(request: CreateTodoRequest): EmptyResult<DataError> =
        repository.create(request)
}
