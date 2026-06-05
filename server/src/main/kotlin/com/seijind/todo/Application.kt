package com.seijind.todo

import com.seijind.todo.plugins.configureKoin
import com.seijind.todo.plugins.configureRouting
import com.seijind.todo.plugins.configureSerialization
import com.seijind.todo.plugins.configureStatusPages
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureStatusPages()
    configureRouting()
}
