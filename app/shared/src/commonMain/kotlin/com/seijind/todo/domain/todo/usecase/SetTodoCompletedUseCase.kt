package com.seijind.todo.domain.todo.usecase

import com.seijind.todo.domain.todo.TodoRepository
import com.seijind.todo.domain.util.DataError
import com.seijind.todo.util.EmptyResult

class SetTodoCompletedUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String, isCompleted: Boolean): EmptyResult<DataError> =
        repository.setCompleted(id, isCompleted)
}
