package com.seijind.todo.ui.settings

data class SettingsState(
    val darkTheme: Boolean = false,
)

sealed interface SettingsAction {
    data class DarkThemeChanged(val enabled: Boolean) : SettingsAction
    data object BackClicked : SettingsAction
}

sealed interface SettingsEvent {
    data object NavigateBack : SettingsEvent
}
