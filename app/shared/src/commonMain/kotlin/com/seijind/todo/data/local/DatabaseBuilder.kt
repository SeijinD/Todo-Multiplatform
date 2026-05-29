package com.seijind.todo.data.local

import androidx.room3.RoomDatabase

expect fun getDatabaseBuilder(ctx: Any? = null): RoomDatabase.Builder<AppDatabase>
