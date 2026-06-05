package com.seijind.todo.todo.routes

import com.seijind.todo.dto.TodoDto
import com.seijind.todo.todo.domain.model.Todo

fun Todo.toDto(): TodoDto = TodoDto(
    id = id.value,
    title = title,
    notes = notes,
    isCompleted = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
