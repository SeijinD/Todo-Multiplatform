package com.seijind.todo.ui.detail

data class TodoDetailState(
    val id: String? = null,
    val title: String = "",
    val notes: String = "",
    val isCompleted: Boolean = false,
    val isNew: Boolean = true,
    val isSaving: Boolean = false,
    val titleError: String? = null,
    val errorMessage: String? = null,
)

sealed interface TodoDetailAction {
    data class TitleChanged(val title: String) : TodoDetailAction
    data class NotesChanged(val notes: String) : TodoDetailAction
    data class ToggleCompleted(val isCompleted: Boolean) : TodoDetailAction
    data object Save : TodoDetailAction
    data object Delete : TodoDetailAction
    data object BackClicked : TodoDetailAction
    data object DismissError : TodoDetailAction
}

sealed interface TodoDetailEvent {
    data object NavigateBack : TodoDetailEvent
}
