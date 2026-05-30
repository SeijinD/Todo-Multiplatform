package com.seijind.todo.di.core

import com.seijind.todo.ui.navigation.Navigator
import org.koin.dsl.module

val navigationModule = module {
    single { Navigator() }
}
