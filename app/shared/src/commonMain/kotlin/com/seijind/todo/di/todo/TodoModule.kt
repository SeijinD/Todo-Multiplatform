package com.seijind.todo.di.todo

import com.seijind.todo.domain.todo.usecase.CreateTodoUseCase
import com.seijind.todo.domain.todo.usecase.DeleteTodoUseCase
import com.seijind.todo.domain.todo.usecase.ObserveTodoUseCase
import com.seijind.todo.domain.todo.usecase.ObserveTodosUseCase
import com.seijind.todo.domain.todo.usecase.RefreshTodosUseCase
import com.seijind.todo.domain.todo.usecase.SetTodoCompletedUseCase
import com.seijind.todo.domain.todo.usecase.UpdateTodoUseCase
import com.seijind.todo.ui.detail.TodoDetailViewModel
import com.seijind.todo.ui.list.TodoListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val todoModule = module {
    factoryOf(::ObserveTodosUseCase)
    factoryOf(::RefreshTodosUseCase)
    factoryOf(::SetTodoCompletedUseCase)
    factoryOf(::ObserveTodoUseCase)
    factoryOf(::CreateTodoUseCase)
    factoryOf(::UpdateTodoUseCase)
    factoryOf(::DeleteTodoUseCase)

    viewModelOf(::TodoListViewModel)
    viewModel { (id: String?) ->
        TodoDetailViewModel(
            observeTodo = get(),
            createTodo = get(),
            updateTodo = get(),
            deleteTodo = get(),
            todoId = id,
        )
    }
}
