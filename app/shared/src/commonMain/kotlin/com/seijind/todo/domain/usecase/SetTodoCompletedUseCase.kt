package com.seijind.todo.domain.usecase

import com.seijind.todo.domain.TodoRepository
import com.seijind.todo.util.DataError
import com.seijind.todo.util.EmptyResult

class SetTodoCompletedUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String, isCompleted: Boolean): EmptyResult<DataError> =
        repository.setCompleted(id, isCompleted)
}
