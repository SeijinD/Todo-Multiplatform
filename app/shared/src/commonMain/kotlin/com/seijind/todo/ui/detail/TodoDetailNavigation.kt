package com.seijind.todo.ui.detail

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.seijind.todo.ui.navigation.Routes
import com.seijind.todo.ui.util.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<NavKey>.todoDetailScreen(
    onNavigateBack: () -> Unit,
) {
    entry<Routes.TodoDetail> { route ->
        val viewModel = koinViewModel<TodoDetailViewModel> { parametersOf(route.id) }
        val state by viewModel.state.collectAsStateWithLifecycle()

        ObserveAsEvents(viewModel.events) { event ->
            when (event) {
                TodoDetailEvent.NavigateBack -> onNavigateBack()
            }
        }

        TodoDetailScreen(state = state, onAction = viewModel::onAction)
    }
}
