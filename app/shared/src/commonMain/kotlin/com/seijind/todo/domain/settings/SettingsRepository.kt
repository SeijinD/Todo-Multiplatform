package com.seijind.todo.domain.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun observeDarkTheme(): Flow<Boolean>

    suspend fun setDarkTheme(enabled: Boolean)
}
