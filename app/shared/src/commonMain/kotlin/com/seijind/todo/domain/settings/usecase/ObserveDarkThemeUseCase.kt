package com.seijind.todo.domain.settings.usecase

import com.seijind.todo.domain.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class ObserveDarkThemeUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<Boolean> = repository.observeDarkTheme()
}
