package com.seijind.todo.data.todo.local

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "todos",
    indices = [
        Index("createdAt"),
        Index("isCompleted"),
    ]
)
data class TodoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val notes: String,
    val isCompleted: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)
