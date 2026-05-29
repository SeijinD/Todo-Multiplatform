package com.seijind.todo.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seijind.todo.domain.TodoRepository
import com.seijind.todo.dto.CreateTodoRequest
import com.seijind.todo.dto.UpdateTodoRequest
import com.seijind.todo.util.Result
import com.seijind.todo.util.TodoError
import com.seijind.todo.util.validateTitle
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
    private val repository: TodoRepository,
    private val todoId: String?,
) : ViewModel() {

    private val _state = MutableStateFlow(TodoDetailState(id = todoId, isNew = todoId == null))
    val state: StateFlow<TodoDetailState> = _state.asStateFlow()

    private val _events = Channel<TodoDetailEvent>(Channel.BUFFERED)
    val events: Flow<TodoDetailEvent> = _events.receiveAsFlow()

    init {
        if (todoId != null) loadTodo(todoId)
    }

    private fun loadTodo(id: String) {
        viewModelScope.launch {
            val todo = repository.observeTodo(id).first() ?: return@launch
            _state.update {
                it.copy(
                    title = todo.title,
                    notes = todo.notes,
                    isCompleted = todo.isCompleted,
                    isNew = false,
                )
            }
        }
    }

    fun onAction(action: TodoDetailAction) {
        when (action) {
            is TodoDetailAction.TitleChanged -> _state.update { it.copy(title = action.title, titleError = null) }
            is TodoDetailAction.NotesChanged -> _state.update { it.copy(notes = action.notes) }
            is TodoDetailAction.ToggleCompleted -> _state.update { it.copy(isCompleted = action.isCompleted) }
            TodoDetailAction.Save -> save()
            TodoDetailAction.Delete -> delete()
            TodoDetailAction.BackClicked -> _events.trySend(TodoDetailEvent.NavigateBack)
            TodoDetailAction.DismissError -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun save() {
        val current = _state.value
        val validation = validateTitle(current.title)
        if (validation is Result.Error) {
            _state.update {
                it.copy(
                    titleError = when (validation.error) {
                        TodoError.EMPTY_TITLE -> "Title cannot be empty"
                        TodoError.TITLE_TOO_LONG -> "Title is too long"
                        else -> "Invalid title"
                    },
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, errorMessage = null) }
            val result = if (current.id == null) {
                repository.create(
                    CreateTodoRequest(title = current.title.trim(), notes = current.notes.ifBlank { null }),
                )
            } else {
                repository.update(
                    id = current.id,
                    request = UpdateTodoRequest(
                        title = current.title.trim(),
                        notes = current.notes,
                        isCompleted = current.isCompleted,
                    ),
                )
            }
            when (result) {
                is Result.Success -> _events.send(TodoDetailEvent.NavigateBack)
                is Result.Error -> _state.update { it.copy(isSaving = false, errorMessage = "Failed to save") }
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
            when (repository.delete(id)) {
                is Result.Success -> _events.send(TodoDetailEvent.NavigateBack)
                is Result.Error -> _state.update { it.copy(isSaving = false, errorMessage = "Failed to delete") }
            }
        }
    }
}
