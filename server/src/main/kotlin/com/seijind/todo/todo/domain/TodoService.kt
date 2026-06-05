package com.seijind.todo.todo.domain

import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.todo.domain.model.Todo
import com.seijind.todo.todo.domain.model.TodoId
import com.seijind.todo.util.EmptyResult
import com.seijind.todo.util.Result
import com.seijind.todo.util.TodoError
import com.seijind.todo.util.validateNotes
import com.seijind.todo.util.validateTitle
import java.util.UUID

class TodoService(private val repository: TodoRepository) {

    suspend fun all(): List<Todo> = repository.all()

    suspend fun get(id: String): Result<Todo, TodoError> =
        repository.get(TodoId(id))?.let { Result.Success(it) } ?: Result.Error(TodoError.NOT_FOUND)

    suspend fun create(request: CreateTodoRequest): Result<Todo, TodoError> {
        validate(request.title, request.notes)?.let { return Result.Error(it) }
        val now = System.currentTimeMillis()
        val todo = Todo(
            id = TodoId(UUID.randomUUID().toString()),
            title = request.title.trim(),
            notes = request.notes?.trim(),
            isCompleted = false,
            createdAt = now,
            updatedAt = now,
        )
        return Result.Success(repository.save(todo))
    }

    suspend fun update(id: String, request: UpdateTodoRequest): Result<Todo, TodoError> {
        val existing = repository.get(TodoId(id)) ?: return Result.Error(TodoError.NOT_FOUND)
        validate(request.title, request.notes)?.let { return Result.Error(it) }
        val newNotes = request.notes
        val updated = existing.copy(
            title = request.title?.trim() ?: existing.title,
            notes = if (newNotes != null) newNotes.trim() else existing.notes,
            isCompleted = request.isCompleted ?: existing.isCompleted,
            updatedAt = System.currentTimeMillis(),
        )
        return Result.Success(repository.save(updated))
    }

    suspend fun delete(id: String): EmptyResult<TodoError> =
        if (repository.delete(TodoId(id))) Result.Success(Unit) else Result.Error(TodoError.NOT_FOUND)

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
