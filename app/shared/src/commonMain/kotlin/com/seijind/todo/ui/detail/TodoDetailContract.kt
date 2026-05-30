package com.seijind.todo.ui.detail

import com.seijind.todo.ui.core.util.UiText

data class TodoDetailState(
    val id: String? = null,
    val title: String = "",
    val notes: String = "",
    val isCompleted: Boolean = false,
    val isSaving: Boolean = false,
    val titleError: UiText? = null,
    val notesError: UiText? = null,
    val errorMessage: UiText? = null,
) {
    val isNew: Boolean get() = id == null
}

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
