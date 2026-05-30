package com.seijind.todo.di

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
            domainModule,
            presentationModule,
        )
    }
}
