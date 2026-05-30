package com.seijind.todo.util

object TodoLimits {
    const val TITLE_MAX_LENGTH = 140
    const val NOTES_MAX_LENGTH = 4000
}

fun validateTitle(title: String): EmptyResult<TodoError> {
    val trimmed = title.trim()
    return when {
        trimmed.isEmpty() -> Result.Error(TodoError.EMPTY_TITLE)
        trimmed.length > TodoLimits.TITLE_MAX_LENGTH -> Result.Error(TodoError.TITLE_TOO_LONG)
        else -> Result.Success(Unit)
    }
}

fun validateNotes(notes: String): EmptyResult<TodoError> = when {
    notes.length > TodoLimits.NOTES_MAX_LENGTH -> Result.Error(TodoError.NOTES_TOO_LONG)
    else -> Result.Success(Unit)
}
