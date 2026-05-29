package com.seijind.todo.data.local

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual fun getDatabaseBuilder(ctx: Any?): RoomDatabase.Builder<AppDatabase> {
    val dbParent = File(System.getProperty("user.home"), ".todo")
    if (!dbParent.exists()) dbParent.mkdirs()
    val dbFile = File(dbParent, "todo.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
        factory = { AppDatabaseConstructor.initialize() },
    ).setDriver(BundledSQLiteDriver()).fallbackToDestructiveMigration(true)
}
