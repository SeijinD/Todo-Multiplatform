package com.seijind.todo

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.seijind.todo.domain.settings.usecase.ObserveDarkThemeUseCase
import com.seijind.todo.ui.navigation.AppNavigation
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val observeDarkTheme = koinInject<ObserveDarkThemeUseCase>()
    val darkTheme by remember { observeDarkTheme() }.collectAsState(initial = false)

    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
    ) {
        AppNavigation()
    }
}
