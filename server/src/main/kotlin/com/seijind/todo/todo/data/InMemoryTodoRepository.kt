package com.seijind.todo.todo.data

import com.seijind.todo.todo.domain.TodoRepository
import com.seijind.todo.todo.domain.model.Todo
import com.seijind.todo.todo.domain.model.TodoId
import java.util.concurrent.ConcurrentHashMap

/** Thread-safe in-memory storage. Swap for a DB-backed repository without touching service/routes. */
class InMemoryTodoRepository : TodoRepository {

    private val todos = ConcurrentHashMap<String, Todo>()

    override suspend fun all(): List<Todo> = todos.values.sortedByDescending { it.createdAt }

    override suspend fun get(id: TodoId): Todo? = todos[id.value]

    override suspend fun save(todo: Todo): Todo {
        todos[todo.id.value] = todo
        return todo
    }

    override suspend fun delete(id: TodoId): Boolean = todos.remove(id.value) != null
}
