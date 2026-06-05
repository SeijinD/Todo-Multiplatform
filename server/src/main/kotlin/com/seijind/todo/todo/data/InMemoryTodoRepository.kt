package com.seijind.todo.todo.data

import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.TodoDto
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.todo.domain.TodoRepository
import com.seijind.todo.util.EmptyResult
import com.seijind.todo.util.Result
import com.seijind.todo.util.TodoError
import com.seijind.todo.util.validateNotes
import com.seijind.todo.util.validateTitle
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/** Thread-safe in-memory implementation. Swap for a DB-backed repository without touching routes. */
class InMemoryTodoRepository : TodoRepository {

    private val todos = ConcurrentHashMap<String, TodoDto>()

    override suspend fun all(): List<TodoDto> = todos.values.sortedByDescending { it.createdAt }

    override suspend fun get(id: String): Result<TodoDto, TodoError> {
        val todo = todos[id] ?: return Result.Error(TodoError.NOT_FOUND)
        return Result.Success(todo)
    }

    override suspend fun create(request: CreateTodoRequest): Result<TodoDto, TodoError> {
        validate(request.title, request.notes)?.let { return Result.Error(it) }
        val now = System.currentTimeMillis()
        val todo = TodoDto(
            id = UUID.randomUUID().toString(),
            title = request.title.trim(),
            notes = request.notes?.trim(),
            isCompleted = false,
            createdAt = now,
            updatedAt = now,
        )
        todos[todo.id] = todo
        return Result.Success(todo)
    }

    override suspend fun update(id: String, request: UpdateTodoRequest): Result<TodoDto, TodoError> {
        val existing = todos[id] ?: return Result.Error(TodoError.NOT_FOUND)
        validate(request.title, request.notes)?.let { return Result.Error(it) }
        val newNotes = request.notes
        val updated = existing.copy(
            title = request.title?.trim() ?: existing.title,
            notes = if (newNotes != null) newNotes.trim() else existing.notes,
            isCompleted = request.isCompleted ?: existing.isCompleted,
            updatedAt = System.currentTimeMillis(),
        )
        todos[id] = updated
        return Result.Success(updated)
    }

    override suspend fun delete(id: String): EmptyResult<TodoError> {
        if (todos.remove(id) == null) return Result.Error(TodoError.NOT_FOUND)
        return Result.Success(Unit)
    }

    private fun validate(title: String?, notes: String?): TodoError? {
        if (title != null) {
            (validateTitle(title) as? Result.Error)?.let { return it.error }
        }
        if (notes != null) {
            (validateNotes(notes) as? Result.Error)?.let { return it.error }
        }
        return null
    }
}
