package com.seijind.todo.dto

import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
    val id: String,
    val title: String,
    val notes: String? = null,
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long,
)
