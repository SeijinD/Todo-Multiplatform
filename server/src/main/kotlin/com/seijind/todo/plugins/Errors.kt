package com.seijind.todo.plugins

import com.seijind.todo.dto.ErrorResponse
import com.seijind.todo.util.TodoError
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun TodoError.toHttpStatus(): HttpStatusCode = when (this) {
    TodoError.EMPTY_TITLE,
    TodoError.TITLE_TOO_LONG,
    TodoError.NOTES_TOO_LONG -> HttpStatusCode.BadRequest
    TodoError.NOT_FOUND -> HttpStatusCode.NotFound
    TodoError.SAVE_FAILED,
    TodoError.DELETE_FAILED,
    TodoError.UNKNOWN -> HttpStatusCode.InternalServerError
}

fun TodoError.toErrorResponse(): ErrorResponse = ErrorResponse(code = name)

suspend fun ApplicationCall.respondError(error: TodoError) {
    respond(error.toHttpStatus(), error.toErrorResponse())
}

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(code = "BAD_REQUEST"))
        }
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(code = TodoError.UNKNOWN.name, message = cause.message),
            )
        }
    }
}
