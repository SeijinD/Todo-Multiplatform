package com.seijind.todo.data.local

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase

actual fun getDatabaseBuilder(ctx: Any?): RoomDatabase.Builder<AppDatabase> {
    val appContext = (ctx as Context).applicationContext
    val dbFile = appContext.getDatabasePath("todo.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    ).fallbackToDestructiveMigration(true)
}
