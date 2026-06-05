package com.seijind.todo.todo.domain

import com.seijind.todo.todo.domain.model.Todo
import com.seijind.todo.todo.domain.model.TodoId

/** Pure storage abstraction. No business rules, no validation — those live in TodoService. */
interface TodoRepository {

    suspend fun all(): List<Todo>

    suspend fun get(id: TodoId): Todo?

    suspend fun save(todo: Todo): Todo

    suspend fun delete(id: TodoId): Boolean
}
