package com.seijind.todo.domain.todo.usecase

import com.seijind.todo.domain.todo.TodoRepository
import com.seijind.todo.domain.util.DataError
import com.seijind.todo.util.EmptyResult

class DeleteTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String): EmptyResult<DataError> = repository.delete(id)
}
