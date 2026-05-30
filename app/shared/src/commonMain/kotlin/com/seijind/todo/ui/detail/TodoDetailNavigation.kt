package com.seijind.todo.ui.detail

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.seijind.todo.ui.detail.composable.TodoDetailScreen
import com.seijind.todo.ui.navigation.Navigator
import com.seijind.todo.ui.navigation.Routes
import com.seijind.todo.ui.core.util.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<NavKey>.todoDetailScreen(
    navigator: Navigator,
) {
    entry<Routes.TodoDetail> { route ->
        val viewModel = koinViewModel<TodoDetailViewModel> { parametersOf(route.id) }
        val state by viewModel.state.collectAsStateWithLifecycle()

        ObserveAsEvents(viewModel.events) { event ->
            when (event) {
                TodoDetailEvent.NavigateBack -> navigator.goBack()
            }
        }

        TodoDetailScreen(state = state, onAction = viewModel::onAction)
    }
}
