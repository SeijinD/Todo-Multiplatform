package com.seijind.todo.domain.settings.usecase

import com.seijind.todo.domain.settings.SettingsRepository

class SetDarkThemeUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(enabled: Boolean) = repository.setDarkTheme(enabled)
}
