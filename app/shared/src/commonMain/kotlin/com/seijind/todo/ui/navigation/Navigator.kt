package com.seijind.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

class Navigator(
    val backStack: NavBackStack<NavKey>,
) {
    val currentRoute: NavKey?
        get() = backStack.lastOrNull()

    private val timeSource = TimeSource.Monotonic
    private var lastNavigationMark: TimeMark? = null

    private fun canNavigate(): Boolean {
        val last = lastNavigationMark
        if (last != null && last.elapsedNow() < DEBOUNCE) return false
        lastNavigationMark = timeSource.markNow()
        return true
    }

    /** Push [route] onto the stack. No-op if debounced or already on top. */
    fun navigateTo(route: NavKey) {
        if (!canNavigate()) return
        if (backStack.lastOrNull() == route) return
        backStack.add(route)
    }

    /** Pop the top entry. Never removes the start destination. */
    fun goBack() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    /** Replace the top entry with [route] (no new history entry). */
    fun replaceTop(route: NavKey) {
        if (backStack.isEmpty()) backStack.add(route)
        else backStack[backStack.lastIndex] = route
    }

    /**
     * Push [route] after popping back to [popUpTo].
     * @param inclusive also removes [popUpTo] itself.
     */
    fun navigateAndPopUpTo(route: NavKey, popUpTo: NavKey, inclusive: Boolean = false) {
        if (!canNavigate()) return
        val index = backStack.indexOfFirst { it::class == popUpTo::class }
        if (index != -1) {
            val removeFrom = if (inclusive) index else index + 1
            if (removeFrom < backStack.size) {
                backStack.subList(removeFrom, backStack.size).clear()
            }
        }
        backStack.add(route)
    }

    /** Drop everything above the start destination. */
    fun clearToRoot() {
        if (backStack.size > 1) backStack.subList(1, backStack.size).clear()
    }

    private companion object {
        val DEBOUNCE = 350.milliseconds
    }
}

/** Creates a [Navigator] backed by a persistent back stack, scoped to the composition. */
@Composable
fun rememberNavigator(startDestination: NavKey = Routes.TodoList): Navigator {
    val backStack = rememberNavBackStack(NavigationSavedStateConfiguration, startDestination)
    return remember(backStack) { Navigator(backStack) }
}
