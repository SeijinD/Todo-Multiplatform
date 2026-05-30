package com.seijind.todo.data.settings

import com.seijind.todo.domain.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepositoryImpl : SettingsRepository {

    private val darkTheme = MutableStateFlow(false)

    override fun observeDarkTheme(): Flow<Boolean> = darkTheme.asStateFlow()

    override suspend fun setDarkTheme(enabled: Boolean) {
        darkTheme.value = enabled
    }
}
