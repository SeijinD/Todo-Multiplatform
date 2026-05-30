package com.seijind.todo.di

import com.seijind.todo.ui.detail.TodoDetailViewModel
import com.seijind.todo.ui.list.TodoListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
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
