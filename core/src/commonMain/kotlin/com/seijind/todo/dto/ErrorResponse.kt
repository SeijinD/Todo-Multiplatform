package com.seijind.todo.dto

import kotlinx.serialization.Serializable

/**
 * Shared error body contract between server and clients.
 * [code] maps to a domain error name (e.g. TodoError); [message] is human-readable detail.
 */
@Serializable
data class ErrorResponse(
    val code: String,
    val message: String? = null,
)
