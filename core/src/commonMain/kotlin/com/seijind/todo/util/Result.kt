package com.seijind.todo.util

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.seijind.todo.util.Error>(val error: E) : Result<Nothing, E>
}

typealias EmptyResult<E> = Result<Unit, E>

inline fun <T, E : Error, R> Result<T, E>.map(transform: (T) -> R): Result<R, E> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> Result.Error(error)
}

inline fun <T, E : Error, ER : Error> Result<T, E>.mapError(transform: (E) -> ER): Result<T, ER> = when (this) {
    is Result.Success -> Result.Success(data)
    is Result.Error -> Result.Error(transform(error))
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T, E : Error> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> {
    if (this is Result.Error) action(error)
    return this
}

inline fun <T, E : Error, R> Result<T, E>.flatMap(transform: (T) -> Result<R, E>): Result<R, E> = when (this) {
    is Result.Success -> transform(data)
    is Result.Error -> Result.Error(error)
}

inline fun <T, E : Error> Result<T, E>.recover(transform: (E) -> T): T = when (this) {
    is Result.Success -> data
    is Result.Error -> transform(error)
}

fun <T, E : Error> Result<T, E>.asEmptyResult(): EmptyResult<E> = map { }

fun <T, E : Error> Result<T, E>.getOrNull(): T? = (this as? Result.Success)?.data

fun <T, E : Error> Result<T, E>.getErrorOrNull(): E? = (this as? Result.Error)?.error

fun <T, E : Error> Result<T, E>.getOrElse(default: T): T = (this as? Result.Success)?.data ?: default
