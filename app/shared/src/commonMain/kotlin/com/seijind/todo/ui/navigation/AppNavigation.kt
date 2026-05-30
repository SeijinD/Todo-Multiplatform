package com.seijind.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.seijind.todo.ui.detail.todoDetailScreen
import com.seijind.todo.ui.list.todoListScreen
import com.seijind.todo.ui.settings.settingsScreen
import org.koin.compose.koinInject

@Composable
fun AppNavigation() {
    val navigator = koinInject<Navigator>()

    NavDisplay(
        backStack = navigator.backStack,
        onBack = { navigator.goBack() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            todoListScreen(navigator)
            todoDetailScreen(navigator)
            settingsScreen(navigator)
        },
    )
}
