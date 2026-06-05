package com.seijind.todo.domain.todo.usecase

import com.seijind.todo.domain.todo.TodoRepository
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.domain.util.DataError
import com.seijind.todo.util.EmptyResult

class UpdateTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String, request: UpdateTodoRequest): EmptyResult<DataError> =
        repository.update(id, request)
}
