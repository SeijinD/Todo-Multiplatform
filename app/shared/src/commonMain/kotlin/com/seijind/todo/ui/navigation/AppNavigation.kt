package com.seijind.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.seijind.todo.ui.detail.todoDetailScreen
import com.seijind.todo.ui.list.todoListScreen

@Composable
fun AppNavigation() {
    val backStack = remember { mutableStateListOf<NavKey>(Routes.TodoList) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            todoListScreen(
                onNavigateToCreate = { backStack.add(Routes.TodoDetail(id = null)) },
                onNavigateToDetail = { id -> backStack.add(Routes.TodoDetail(id = id)) },
            )
            todoDetailScreen(
                onNavigateBack = { backStack.removeLastOrNull() },
            )
        },
    )
}
