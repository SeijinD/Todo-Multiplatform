package com.seijind.todo.data

import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.TodoDto
import com.seijind.todo.dto.UpdateTodoRequest
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object InMemoryTodoStore {

    private val todos = ConcurrentHashMap<String, TodoDto>()

    fun all(): List<TodoDto> = todos.values.sortedByDescending { it.createdAt }

    fun get(id: String): TodoDto? = todos[id]

    fun create(request: CreateTodoRequest): TodoDto {
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
        return todo
    }

    fun update(id: String, request: UpdateTodoRequest): TodoDto? {
        val existing = todos[id] ?: return null
        val newNotes = request.notes
        val updated = existing.copy(
            title = request.title?.trim() ?: existing.title,
            notes = if (newNotes != null) newNotes.trim() else existing.notes,
            isCompleted = request.isCompleted ?: existing.isCompleted,
            updatedAt = System.currentTimeMillis(),
        )
        todos[id] = updated
        return updated
    }

    fun delete(id: String): Boolean = todos.remove(id) != null
}
