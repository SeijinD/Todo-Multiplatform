package com.seijind.todo.di

import com.seijind.todo.domain.usecase.CreateTodoUseCase
import com.seijind.todo.domain.usecase.DeleteTodoUseCase
import com.seijind.todo.domain.usecase.ObserveTodoUseCase
import com.seijind.todo.domain.usecase.ObserveTodosUseCase
import com.seijind.todo.domain.usecase.RefreshTodosUseCase
import com.seijind.todo.domain.usecase.SetTodoCompletedUseCase
import com.seijind.todo.domain.usecase.UpdateTodoUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::ObserveTodosUseCase)
    factoryOf(::ObserveTodoUseCase)
    factoryOf(::RefreshTodosUseCase)
    factoryOf(::CreateTodoUseCase)
    factoryOf(::UpdateTodoUseCase)
    factoryOf(::SetTodoCompletedUseCase)
    factoryOf(::DeleteTodoUseCase)
}
