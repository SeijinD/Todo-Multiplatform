package com.seijind.todo.di

import com.seijind.todo.data.local.AppDatabase
import com.seijind.todo.data.local.TodoDao
import org.koin.dsl.module

val databaseModule = module {
    single<TodoDao> { get<AppDatabase>().todoDao() }
}
