package com.seijind.todo.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.seijind.todo.model.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    state: TodoListState,
    onAction: (TodoListAction) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Todos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(TodoListAction.AddClicked) }) {
                Text("+")
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            when {
                state.isRefreshing && state.todos.isEmpty() -> CircularProgressIndicator()
                state.todos.isEmpty() -> Text("No todos yet. Tap + to add one.")
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.todos, key = { it.id }) { todo ->
                        TodoRow(
                            todo = todo,
                            onToggle = { onAction(TodoListAction.ToggleCompleted(todo.id, it)) },
                            onClick = { onAction(TodoListAction.TodoClicked(todo.id)) },
                            onDelete = { onAction(TodoListAction.DeleteTodo(todo.id)) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TodoRow(
    todo: Todo,
    onToggle: (Boolean) -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = todo.isCompleted, onCheckedChange = onToggle)
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null,
            )
            if (todo.notes.isNotBlank()) {
                Text(text = todo.notes, style = MaterialTheme.typography.bodySmall, maxLines = 1)
            }
        }
        IconButton(onClick = onDelete) {
            Text("✕")
        }
    }
}
