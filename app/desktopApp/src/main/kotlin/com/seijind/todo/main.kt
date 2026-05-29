package com.seijind.todo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.seijind.todo.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Todo Multiplatform",
        ) {
            App()
        }
    }
}