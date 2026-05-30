package com.seijind.todo.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Routes : NavKey {

    @Serializable
    data object TodoList : Routes

    @Serializable
    data class TodoDetail(val id: String? = null) : Routes

    @Serializable
    data object Settings : Routes
}
