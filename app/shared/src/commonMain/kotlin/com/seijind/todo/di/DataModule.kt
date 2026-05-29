package com.seijind.todo.di

import com.seijind.todo.data.TodoRepositoryImpl
import com.seijind.todo.domain.TodoRepository
import org.koin.dsl.module

val dataModule = module {
    single<TodoRepository> { TodoRepositoryImpl(get(), get(), get()) }
}
