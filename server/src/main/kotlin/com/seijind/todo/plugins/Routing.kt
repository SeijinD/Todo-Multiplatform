package com.seijind.todo.plugins

import com.seijind.todo.todo.domain.TodoService
import com.seijind.todo.todo.routes.todoRoutes
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val service by inject<TodoService>()
    routing {
        get("/") {
            call.respondText("Todo server running")
        }
        todoRoutes(service)
    }
}
