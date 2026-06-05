package com.seijind.todo.domain.todo.usecase

import com.seijind.todo.domain.todo.TodoRepository
import com.seijind.todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

class ObserveTodosUseCase(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<Todo>> = repository.observeTodos()
}
