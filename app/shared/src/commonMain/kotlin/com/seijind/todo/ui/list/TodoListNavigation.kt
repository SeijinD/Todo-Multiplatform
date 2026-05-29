package com.seijind.todo.ui.list

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.seijind.todo.ui.navigation.Routes
import com.seijind.todo.ui.util.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

fun EntryProviderScope<NavKey>.todoListScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
) {
    entry<Routes.TodoList> {
        val viewModel = koinViewModel<TodoListViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        ObserveAsEvents(viewModel.events) { event ->
            when (event) {
                TodoListEvent.NavigateToCreate -> onNavigateToCreate()
                is TodoListEvent.NavigateToDetail -> onNavigateToDetail(event.id)
            }
        }

        TodoListScreen(state = state, onAction = viewModel::onAction)
    }
}
