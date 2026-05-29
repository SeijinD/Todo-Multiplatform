package com.seijind.todo.di

import com.seijind.todo.data.local.AppDatabase
import com.seijind.todo.data.local.getDatabaseBuilder
import com.seijind.todo.util.AppDispatchers
import com.seijind.todo.util.StandardAppDispatchers
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppDispatchers> {
        StandardAppDispatchers(
            main = Dispatchers.Main,
            io = Dispatchers.IO,
            default = Dispatchers.Default,
        )
    }
    single<HttpClientEngine> { Android.create() }
    single(named("baseUrl")) { "http://10.0.2.2:8080/" }
    single<AppDatabase> {
        getDatabaseBuilder(androidContext())
            .setQueryCoroutineContext(get<AppDispatchers>().io)
            .build()
    }
}
