package com.seijind.todo.di.core

import com.seijind.todo.di.settings.settingsModule
import com.seijind.todo.di.todo.todoModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

private var initialized = false

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    if (initialized) return
    initialized = true

    startKoin {
        appDeclaration()
        modules(
            platformModule,
            networkModule,
            databaseModule,
            dataModule,
            todoModule,
            settingsModule,
        )
    }
}
