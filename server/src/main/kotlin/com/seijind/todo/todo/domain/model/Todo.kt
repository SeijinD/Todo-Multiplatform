package com.seijind.todo.todo.domain.model

@JvmInline
value class TodoId(val value: String)

data class Todo(
    val id: TodoId,
    val title: String,
    val notes: String?,
    val isCompleted: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)
