package com.seijind.todo.ui.list

import com.seijind.todo.model.Todo

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
)

sealed interface TodoListAction {
    data object Refresh : TodoListAction
    data object AddClicked : TodoListAction
    data class TodoClicked(val id: String) : TodoListAction
    data class ToggleCompleted(val id: String, val isCompleted: Boolean) : TodoListAction
    data class DeleteTodo(val id: String) : TodoListAction
    data object DismissError : TodoListAction
}

sealed interface TodoListEvent {
    data object NavigateToCreate : TodoListEvent
    data class NavigateToDetail(val id: String) : TodoListEvent
}
