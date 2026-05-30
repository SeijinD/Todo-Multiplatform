package com.seijind.todo.domain.usecase

import com.seijind.todo.domain.TodoRepository
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.util.DataError
import com.seijind.todo.util.EmptyResult

class UpdateTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String, request: UpdateTodoRequest): EmptyResult<DataError> =
        repository.update(id, request)
}
