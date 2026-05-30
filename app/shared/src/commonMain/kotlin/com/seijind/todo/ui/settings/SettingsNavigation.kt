package com.seijind.todo.ui.settings

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.seijind.todo.ui.navigation.Navigator
import com.seijind.todo.ui.navigation.Routes
import com.seijind.todo.ui.settings.composable.SettingsScreen
import com.seijind.todo.ui.core.util.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

fun EntryProviderScope<NavKey>.settingsScreen(
    navigator: Navigator,
) {
    entry<Routes.Settings> {
        val viewModel = koinViewModel<SettingsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        ObserveAsEvents(viewModel.events) { event ->
            when (event) {
                SettingsEvent.NavigateBack -> navigator.goBack()
            }
        }

        SettingsScreen(state = state, onAction = viewModel::onAction)
    }
}
