package com.seijind.todo.di

import com.seijind.todo.todo.data.InMemoryTodoRepository
import com.seijind.todo.todo.domain.TodoRepository
import com.seijind.todo.todo.domain.TodoService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val todoModule = module {
    singleOf(::InMemoryTodoRepository) bind TodoRepository::class
    singleOf(::TodoService)
}
