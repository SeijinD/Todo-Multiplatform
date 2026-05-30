package com.seijind.todo.di.core

import com.seijind.todo.data.todo.remote.TodoApi
import com.seijind.todo.util.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClientFactory.create(
            engine = get<HttpClientEngine>(),
            baseUrl = get(named(QUALIFIER_BASE_URL)),
        )
    }
    single { TodoApi(get()) }
}
