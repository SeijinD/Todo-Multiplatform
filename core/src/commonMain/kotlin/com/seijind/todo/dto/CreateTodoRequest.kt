package com.seijind.todo.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTodoRequest(
    val title: String,
    val notes: String? = null,
)
