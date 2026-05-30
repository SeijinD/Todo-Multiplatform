package com.seijind.todo.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seijind.todo.domain.usecase.DeleteTodoUseCase
import com.seijind.todo.domain.usecase.ObserveTodosUseCase
import com.seijind.todo.domain.usecase.RefreshTodosUseCase
import com.seijind.todo.domain.usecase.SetTodoCompletedUseCase
import com.seijind.todo.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val observeTodos: ObserveTodosUseCase,
    private val refreshTodos: RefreshTodosUseCase,
    private val setTodoCompleted: SetTodoCompletedUseCase,
    private val deleteTodo: DeleteTodoUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TodoListState())
    val state: StateFlow<TodoListState> = _state
        .onStart {
            startObservingTodos()
            refresh()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TodoListState())

    private val _events = Channel<TodoListEvent>(Channel.BUFFERED)
    val events: Flow<TodoListEvent> = _events.receiveAsFlow()

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
                    errorMessage = (result as? Result.Error)?.let { "Failed to load todos" },
                )
            }
        }
    }

    private fun toggle(id: String, isCompleted: Boolean) {
        viewModelScope.launch {
            if (setTodoCompleted(id, isCompleted) is Result.Error) {
                _state.update { it.copy(errorMessage = "Failed to update todo") }
            }
        }
    }

    private fun delete(id: String) {
        viewModelScope.launch {
            if (deleteTodo(id) is Result.Error) {
                _state.update { it.copy(errorMessage = "Failed to delete todo") }
            }
        }
    }
}
