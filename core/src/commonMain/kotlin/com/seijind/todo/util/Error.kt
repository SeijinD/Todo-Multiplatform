package com.seijind.todo.util

interface Error

enum class TodoError : Error {
    EMPTY_TITLE,
    TITLE_TOO_LONG,
    NOTES_TOO_LONG,
    NOT_FOUND,
    SAVE_FAILED,
    DELETE_FAILED,
    UNKNOWN
}
