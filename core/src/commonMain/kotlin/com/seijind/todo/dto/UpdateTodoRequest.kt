package com.seijind.todo.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoRequest(
    val title: String? = null,
    val notes: String? = null,
    val isCompleted: Boolean? = null,
)
