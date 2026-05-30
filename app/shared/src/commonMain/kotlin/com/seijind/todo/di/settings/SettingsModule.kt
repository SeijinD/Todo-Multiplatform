package com.seijind.todo.di.settings

import com.seijind.todo.data.settings.SettingsRepositoryImpl
import com.seijind.todo.domain.settings.SettingsRepository
import com.seijind.todo.domain.settings.usecase.ObserveDarkThemeUseCase
import com.seijind.todo.domain.settings.usecase.SetDarkThemeUseCase
import com.seijind.todo.ui.settings.SettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val settingsModule = module {
    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class

    factoryOf(::ObserveDarkThemeUseCase)
    factoryOf(::SetDarkThemeUseCase)

    viewModelOf(::SettingsViewModel)
}
