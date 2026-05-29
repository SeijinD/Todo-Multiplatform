package com.seijind.todo.routes

import com.seijind.todo.data.InMemoryTodoStore
import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.UpdateTodoRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.todoRoutes() {
    route("/todos") {
        get {
            call.respond(InMemoryTodoStore.all())
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val todo = InMemoryTodoStore.get(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(todo)
        }

        post {
            val request = call.receive<CreateTodoRequest>()
            if (request.title.isBlank()) {
                return@post call.respond(HttpStatusCode.BadRequest)
            }
            call.respond(HttpStatusCode.Created, InMemoryTodoStore.create(request))
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val request = call.receive<UpdateTodoRequest>()
            val updated = InMemoryTodoStore.update(id, request)
                ?: return@put call.respond(HttpStatusCode.NotFound)
            call.respond(updated)
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (InMemoryTodoStore.delete(id)) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
