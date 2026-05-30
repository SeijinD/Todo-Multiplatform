package com.seijind.todo.domain.usecase

import com.seijind.todo.domain.TodoRepository
import com.seijind.todo.util.DataError
import com.seijind.todo.util.EmptyResult

class DeleteTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String): EmptyResult<DataError> = repository.delete(id)
}
