package com.seijind.todo.data.local

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import org.w3c.dom.Worker

actual fun getDatabaseBuilder(ctx: Any?): RoomDatabase.Builder<AppDatabase> {
    val worker = try {
        createSQLiteWorker()
    } catch (e: Exception) {
        null
    }

    val builder = Room.databaseBuilder<AppDatabase>(
        name = "todo.db",
        factory = { AppDatabaseConstructor.initialize() },
    )

    if (worker != null) {
        builder.setDriver(WebWorkerSQLiteDriver(worker))
    }

    return builder.fallbackToDestructiveMigration(true)
}

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("() => { try { return new Worker(new URL('sqlite-wasm-worker/worker.js', import.meta.url), { type: 'module' }); } catch (e) { return null; } }")
private external fun createSQLiteWorker(): Worker?
