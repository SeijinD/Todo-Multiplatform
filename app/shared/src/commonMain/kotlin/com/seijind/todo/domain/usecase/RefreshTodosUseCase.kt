package com.seijind.todo.domain.usecase

import com.seijind.todo.domain.TodoRepository
import com.seijind.todo.util.DataError
import com.seijind.todo.util.EmptyResult

class RefreshTodosUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(): EmptyResult<DataError> = repository.refresh()
}
