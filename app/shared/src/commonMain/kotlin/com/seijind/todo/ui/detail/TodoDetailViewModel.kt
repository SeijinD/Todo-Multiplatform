package com.seijind.todo.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seijind.todo.domain.todo.usecase.CreateTodoUseCase
import com.seijind.todo.domain.todo.usecase.DeleteTodoUseCase
import com.seijind.todo.domain.todo.usecase.ObserveTodoUseCase
import com.seijind.todo.domain.todo.usecase.UpdateTodoUseCase
import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.ui.core.util.UiText
import com.seijind.todo.ui.core.util.toUiText
import com.seijind.todo.util.Result
import com.seijind.todo.util.validateNotes
import com.seijind.todo.util.validateTitle
import todomultiplatform.app.shared.generated.resources.Res
import todomultiplatform.app.shared.generated.resources.error_todo_save_failed
import todomultiplatform.app.shared.generated.resources.error_todo_delete_failed
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoDetailViewModel(
    private val observeTodo: ObserveTodoUseCase,
    private val createTodo: CreateTodoUseCase,
    private val updateTodo: UpdateTodoUseCase,
    private val deleteTodo: DeleteTodoUseCase,
    todoId: String?,
) : ViewModel() {

    private val _state = MutableStateFlow(TodoDetailState(id = todoId))
    val state: StateFlow<TodoDetailState> = _state.asStateFlow()

    private val _events = Channel<TodoDetailEvent>(Channel.BUFFERED)
    val events: Flow<TodoDetailEvent> = _events.receiveAsFlow()

    init {
        if (todoId != null) loadTodo(todoId)
    }

    private fun loadTodo(id: String) {
        viewModelScope.launch {
            val todo = observeTodo(id).first() ?: return@launch
            _state.update {
                it.copy(
                    title = todo.title,
                    notes = todo.notes,
                    isCompleted = todo.isCompleted,
                )
            }
        }
    }

    fun onAction(action: TodoDetailAction) {
        when (action) {
            is TodoDetailAction.TitleChanged -> _state.update { it.copy(title = action.title, titleError = null) }
            is TodoDetailAction.NotesChanged -> _state.update { it.copy(notes = action.notes, notesError = null) }
            is TodoDetailAction.ToggleCompleted -> _state.update { it.copy(isCompleted = action.isCompleted) }
            TodoDetailAction.Save -> save()
            TodoDetailAction.Delete -> delete()
            TodoDetailAction.BackClicked -> _events.trySend(TodoDetailEvent.NavigateBack)
            TodoDetailAction.DismissError -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun save() {
        val current = _state.value
        val titleValidation = validateTitle(current.title)
        val notesValidation = validateNotes(current.notes)
        if (titleValidation is Result.Error || notesValidation is Result.Error) {
            _state.update {
                it.copy(
                    titleError = (titleValidation as? Result.Error)?.error?.toUiText(),
                    notesError = (notesValidation as? Result.Error)?.error?.toUiText(),
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, errorMessage = null) }
            val result = if (current.id == null) {
                createTodo(
                    CreateTodoRequest(title = current.title.trim(), notes = current.notes.ifBlank { null }),
                )
            } else {
                updateTodo(
                    id = current.id,
                    request = UpdateTodoRequest(
                        title = current.title.trim(),
                        notes = current.notes,
                        isCompleted = current.isCompleted,
                    ),
                )
            }
            when (result) {
                is Result.Success -> _events.trySend(TodoDetailEvent.NavigateBack)
                is Result.Error -> _state.update {
                    it.copy(isSaving = false, errorMessage = UiText.StringRes(Res.string.error_todo_save_failed))
                }
            }
        }
    }

    private fun delete() {
        val id = _state.value.id ?: run {
            _events.trySend(TodoDetailEvent.NavigateBack)
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            when (deleteTodo(id)) {
                is Result.Success -> _events.trySend(TodoDetailEvent.NavigateBack)
                is Result.Error -> _state.update {
                    it.copy(isSaving = false, errorMessage = UiText.StringRes(Res.string.error_todo_delete_failed))
                }
            }
        }
    }
}
