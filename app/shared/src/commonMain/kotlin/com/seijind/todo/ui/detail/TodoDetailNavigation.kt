package com.seijind.todo.ui.detail

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
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

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.todoDetailScreen(
    navigator: Navigator,
) {
    entry<Routes.TodoDetail>(
        metadata = ListDetailSceneStrategy.detailPane(),
    ) { route ->
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
