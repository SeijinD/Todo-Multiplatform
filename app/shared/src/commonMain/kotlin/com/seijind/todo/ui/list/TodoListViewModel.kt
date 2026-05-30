package com.seijind.todo.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seijind.todo.domain.todo.usecase.DeleteTodoUseCase
import com.seijind.todo.domain.todo.usecase.ObserveTodosUseCase
import com.seijind.todo.domain.todo.usecase.RefreshTodosUseCase
import com.seijind.todo.domain.todo.usecase.SetTodoCompletedUseCase
import com.seijind.todo.ui.core.util.toUiText
import com.seijind.todo.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val observeTodos: ObserveTodosUseCase,
    private val refreshTodos: RefreshTodosUseCase,
    private val setTodoCompleted: SetTodoCompletedUseCase,
    private val deleteTodo: DeleteTodoUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TodoListState())
    val state: StateFlow<TodoListState> = _state.asStateFlow()

    private val _events = Channel<TodoListEvent>(Channel.BUFFERED)
    val events: Flow<TodoListEvent> = _events.receiveAsFlow()

    init {
        startObservingTodos()
        refresh()
    }

    private fun startObservingTodos() {
        viewModelScope.launch {
            observeTodos().collect { todos ->
                _state.update { it.copy(todos = todos) }
            }
        }
    }

    fun onAction(action: TodoListAction) {
        when (action) {
            TodoListAction.Refresh -> refresh()
            TodoListAction.AddClicked -> _events.trySend(TodoListEvent.NavigateToCreate)
            is TodoListAction.TodoClicked -> _events.trySend(TodoListEvent.NavigateToDetail(action.id))
            is TodoListAction.ToggleCompleted -> toggle(action.id, action.isCompleted)
            is TodoListAction.DeleteTodo -> delete(action.id)
            TodoListAction.SettingsClicked -> _events.trySend(TodoListEvent.NavigateToSettings)
            TodoListAction.DismissError -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            val result = refreshTodos()
            _state.update {
                it.copy(
                    isRefreshing = false,
                    errorMessage = (result as? Result.Error)?.error?.toUiText(),
                )
            }
        }
    }

    private fun toggle(id: String, isCompleted: Boolean) {
        viewModelScope.launch {
            val result = setTodoCompleted(id, isCompleted)
            if (result is Result.Error) {
                _state.update { it.copy(errorMessage = result.error.toUiText()) }
            }
        }
    }

    private fun delete(id: String) {
        viewModelScope.launch {
            val result = deleteTodo(id)
            if (result is Result.Error) {
                _state.update { it.copy(errorMessage = result.error.toUiText()) }
            }
        }
    }
}
