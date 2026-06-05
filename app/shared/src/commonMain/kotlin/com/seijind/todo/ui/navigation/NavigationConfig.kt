package com.seijind.todo.ui.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 * Back stack persistence config.
 *
 * On non-JVM targets (iOS, Wasm) `rememberNavBackStack` cannot use reflection, so every [NavKey]
 * subtype must be registered for polymorphic serialization. Add new routes here.
 */
val NavigationSavedStateConfiguration: SavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Routes.TodoList::class, Routes.TodoList.serializer())
            subclass(Routes.TodoDetail::class, Routes.TodoDetail.serializer())
            subclass(Routes.Settings::class, Routes.Settings.serializer())
        }
    }
}
