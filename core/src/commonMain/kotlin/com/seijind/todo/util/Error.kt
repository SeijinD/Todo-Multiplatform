package com.seijind.todo.util

interface Error

sealed interface DataError : Error {
    enum class Network : DataError {
        BAD_REQUEST,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        REQUEST_TIMEOUT,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        NOT_FOUND,
        DATABASE_ERROR,
        DISK_FULL,
        UNKNOWN
    }
}

enum class TodoError : Error {
    EMPTY_TITLE,
    TITLE_TOO_LONG,
    NOTES_TOO_LONG,
    NOT_FOUND,
    SAVE_FAILED,
    DELETE_FAILED,
    UNKNOWN
}
