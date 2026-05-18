package com.seijind.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform