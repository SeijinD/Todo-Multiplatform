package com.seijind.todo.di.core

import com.seijind.todo.data.todo.TodoRepositoryImpl
import com.seijind.todo.domain.todo.TodoRepository
import org.koin.dsl.module

val dataModule = module {
    single<TodoRepository> { TodoRepositoryImpl(get(), get(), get()) }
}
