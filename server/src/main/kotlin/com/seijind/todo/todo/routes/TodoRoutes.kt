package com.seijind.todo.todo.routes

import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.ErrorResponse
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.plugins.respondError
import com.seijind.todo.todo.domain.TodoService
import com.seijind.todo.util.Result
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.todoRoutes(service: TodoService) {
    route("/todos") {
        get {
            call.respond(service.all().map { it.toDto() })
        }

        get("/{id}") {
            val id = call.requireId() ?: return@get
            when (val result = service.get(id)) {
                is Result.Success -> call.respond(result.data.toDto())
                is Result.Error -> call.respondError(result.error)
            }
        }

        post {
            val request = call.receive<CreateTodoRequest>()
            when (val result = service.create(request)) {
                is Result.Success -> call.respond(HttpStatusCode.Created, result.data.toDto())
                is Result.Error -> call.respondError(result.error)
            }
        }

        put("/{id}") {
            val id = call.requireId() ?: return@put
            val request = call.receive<UpdateTodoRequest>()
            when (val result = service.update(id, request)) {
                is Result.Success -> call.respond(result.data.toDto())
                is Result.Error -> call.respondError(result.error)
            }
        }

        delete("/{id}") {
            val id = call.requireId() ?: return@delete
            when (val result = service.delete(id)) {
                is Result.Success -> call.respond(HttpStatusCode.NoContent)
                is Result.Error -> call.respondError(result.error)
            }
        }
    }
}

private suspend fun ApplicationCall.requireId(): String? {
    val id = parameters["id"]
    if (id.isNullOrBlank()) {
        respond(HttpStatusCode.BadRequest, ErrorResponse(code = "BAD_REQUEST"))
        return null
    }
    return id
}
