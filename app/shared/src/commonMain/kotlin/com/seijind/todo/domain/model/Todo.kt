package com.seijind.todo.domain.model

data class Todo(
    val id: String,
    val title: String,
    val notes: String,
    val isCompleted: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)
