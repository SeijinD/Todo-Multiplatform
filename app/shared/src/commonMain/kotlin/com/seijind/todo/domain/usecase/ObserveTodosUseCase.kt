package com.seijind.todo.domain.usecase

import com.seijind.todo.domain.TodoRepository
import com.seijind.todo.model.Todo
import kotlinx.coroutines.flow.Flow

class ObserveTodosUseCase(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<Todo>> = repository.observeTodos()
}
