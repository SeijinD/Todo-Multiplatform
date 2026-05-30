package com.seijind.todo.data.todo.mapper

import com.seijind.todo.data.todo.local.TodoEntity
import com.seijind.todo.dto.TodoDto
import com.seijind.todo.model.Todo

fun TodoDto.toDomain(): Todo = Todo(
    id = id,
    title = title,
    notes = notes.orEmpty(),
    isCompleted = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun TodoDto.toEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    notes = notes.orEmpty(),
    isCompleted = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun TodoEntity.toDomain(): Todo = Todo(
    id = id,
    title = title,
    notes = notes,
    isCompleted = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun Todo.toEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    notes = notes,
    isCompleted = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
