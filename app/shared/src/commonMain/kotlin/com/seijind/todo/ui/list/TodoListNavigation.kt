package com.seijind.todo.ui.list

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.seijind.todo.ui.list.composable.TodoListScreen
import com.seijind.todo.ui.navigation.Navigator
import com.seijind.todo.ui.navigation.Routes
import com.seijind.todo.ui.core.util.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.todoListScreen(
    navigator: Navigator,
) {
    entry<Routes.TodoList>(
        metadata = ListDetailSceneStrategy.listPane(),
    ) {
        val viewModel = koinViewModel<TodoListViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        ObserveAsEvents(viewModel.events) { event ->
            when (event) {
                TodoListEvent.NavigateToCreate -> navigator.navigateTo(Routes.TodoDetail(id = null))
                is TodoListEvent.NavigateToDetail -> navigator.navigateTo(Routes.TodoDetail(id = event.id))
                TodoListEvent.NavigateToSettings -> navigator.navigateTo(Routes.Settings)
            }
        }

        TodoListScreen(state = state, onAction = viewModel::onAction)
    }
}
