package com.seijind.todo.di.core

import com.seijind.todo.data.local.AppDatabase
import com.seijind.todo.data.local.getDatabaseBuilder
import com.seijind.todo.util.AppDispatchers
import com.seijind.todo.util.StandardAppDispatchers
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppDispatchers> {
        StandardAppDispatchers(
            main = Dispatchers.Main,
            io = Dispatchers.Default,
            default = Dispatchers.Default,
        )
    }
    single<HttpClientEngine> { Darwin.create() }
    single(named(QUALIFIER_BASE_URL)) { "http://localhost:8080/" }
    single<AppDatabase> {
        getDatabaseBuilder()
            .setQueryCoroutineContext(get<AppDispatchers>().io)
            .build()
    }
}
