package com.seijind.todo.ui.list

import com.seijind.todo.domain.model.Todo
import com.seijind.todo.ui.core.util.UiText

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val isRefreshing: Boolean = false,
    val errorMessage: UiText? = null,
)

sealed interface TodoListAction {
    data object Refresh : TodoListAction
    data object AddClicked : TodoListAction
    data class TodoClicked(val id: String) : TodoListAction
    data class ToggleCompleted(val id: String, val isCompleted: Boolean) : TodoListAction
    data class DeleteTodo(val id: String) : TodoListAction
    data object SettingsClicked : TodoListAction
    data object DismissError : TodoListAction
}

sealed interface TodoListEvent {
    data object NavigateToCreate : TodoListEvent
    data class NavigateToDetail(val id: String) : TodoListEvent
    data object NavigateToSettings : TodoListEvent
}
