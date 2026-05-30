package com.seijind.todo.domain.todo.usecase

import com.seijind.todo.domain.todo.TodoRepository
import com.seijind.todo.model.Todo
import kotlinx.coroutines.flow.Flow

class ObserveTodoUseCase(private val repository: TodoRepository) {
    operator fun invoke(id: String): Flow<Todo?> = repository.observeTodo(id)
}
