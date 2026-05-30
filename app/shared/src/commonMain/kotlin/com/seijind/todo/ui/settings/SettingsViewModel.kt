package com.seijind.todo.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seijind.todo.domain.settings.usecase.ObserveDarkThemeUseCase
import com.seijind.todo.domain.settings.usecase.SetDarkThemeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val observeDarkTheme: ObserveDarkThemeUseCase,
    private val setDarkTheme: SetDarkThemeUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    private val _events = Channel<SettingsEvent>(Channel.BUFFERED)
    val events: Flow<SettingsEvent> = _events.receiveAsFlow()

    init {
        observeTheme()
    }

    private fun observeTheme() {
        viewModelScope.launch {
            observeDarkTheme().collect { enabled ->
                _state.update { it.copy(darkTheme = enabled) }
            }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.DarkThemeChanged -> viewModelScope.launch { setDarkTheme(action.enabled) }
            SettingsAction.BackClicked -> _events.trySend(SettingsEvent.NavigateBack)
        }
    }
}
