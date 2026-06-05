package com.seijind.todo.domain.util

import com.seijind.todo.util.Error

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
