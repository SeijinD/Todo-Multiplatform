package com.seijind.todo.ui.core.util

import androidx.compose.runtime.Composable
import com.seijind.todo.util.DataError
import com.seijind.todo.util.Error
import com.seijind.todo.util.TodoError
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import todomultiplatform.app.shared.generated.resources.Res
import todomultiplatform.app.shared.generated.resources.error_local_database
import todomultiplatform.app.shared.generated.resources.error_local_disk_full
import todomultiplatform.app.shared.generated.resources.error_local_not_found
import todomultiplatform.app.shared.generated.resources.error_network_bad_request
import todomultiplatform.app.shared.generated.resources.error_network_conflict
import todomultiplatform.app.shared.generated.resources.error_network_forbidden
import todomultiplatform.app.shared.generated.resources.error_network_no_internet
import todomultiplatform.app.shared.generated.resources.error_network_not_found
import todomultiplatform.app.shared.generated.resources.error_network_serialization
import todomultiplatform.app.shared.generated.resources.error_network_server
import todomultiplatform.app.shared.generated.resources.error_network_timeout
import todomultiplatform.app.shared.generated.resources.error_network_too_many_requests
import todomultiplatform.app.shared.generated.resources.error_network_unauthorized
import todomultiplatform.app.shared.generated.resources.error_todo_delete_failed
import todomultiplatform.app.shared.generated.resources.error_todo_empty_title
import todomultiplatform.app.shared.generated.resources.error_todo_not_found
import todomultiplatform.app.shared.generated.resources.error_todo_notes_too_long
import todomultiplatform.app.shared.generated.resources.error_todo_save_failed
import todomultiplatform.app.shared.generated.resources.error_todo_title_too_long
import todomultiplatform.app.shared.generated.resources.error_unknown

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    data class StringRes(
        val resource: StringResource,
        val args: List<Any> = emptyList(),
    ) : UiText

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringRes -> stringResource(resource, *args.toTypedArray())
    }

    suspend fun asStringAsync(): String = when (this) {
        is DynamicString -> value
        is StringRes -> getString(resource, *args.toTypedArray())
    }
}

fun Error.toUiText(): UiText = when (this) {
    is DataError -> toUiText()
    is TodoError -> toUiText()
    else -> UiText.StringRes(Res.string.error_unknown)
}

fun DataError.toUiText(): UiText = when (this) {
    DataError.Network.BAD_REQUEST -> UiText.StringRes(Res.string.error_network_bad_request)
    DataError.Network.UNAUTHORIZED -> UiText.StringRes(Res.string.error_network_unauthorized)
    DataError.Network.FORBIDDEN -> UiText.StringRes(Res.string.error_network_forbidden)
    DataError.Network.NOT_FOUND -> UiText.StringRes(Res.string.error_network_not_found)
    DataError.Network.REQUEST_TIMEOUT -> UiText.StringRes(Res.string.error_network_timeout)
    DataError.Network.CONFLICT -> UiText.StringRes(Res.string.error_network_conflict)
    DataError.Network.TOO_MANY_REQUESTS -> UiText.StringRes(Res.string.error_network_too_many_requests)
    DataError.Network.NO_INTERNET -> UiText.StringRes(Res.string.error_network_no_internet)
    DataError.Network.SERVER_ERROR -> UiText.StringRes(Res.string.error_network_server)
    DataError.Network.SERIALIZATION -> UiText.StringRes(Res.string.error_network_serialization)
    DataError.Network.UNKNOWN -> UiText.StringRes(Res.string.error_unknown)
    DataError.Local.NOT_FOUND -> UiText.StringRes(Res.string.error_local_not_found)
    DataError.Local.DATABASE_ERROR -> UiText.StringRes(Res.string.error_local_database)
    DataError.Local.DISK_FULL -> UiText.StringRes(Res.string.error_local_disk_full)
    DataError.Local.UNKNOWN -> UiText.StringRes(Res.string.error_unknown)
}

fun TodoError.toUiText(): UiText = when (this) {
    TodoError.EMPTY_TITLE -> UiText.StringRes(Res.string.error_todo_empty_title)
    TodoError.TITLE_TOO_LONG -> UiText.StringRes(Res.string.error_todo_title_too_long)
    TodoError.NOTES_TOO_LONG -> UiText.StringRes(Res.string.error_todo_notes_too_long)
    TodoError.NOT_FOUND -> UiText.StringRes(Res.string.error_todo_not_found)
    TodoError.SAVE_FAILED -> UiText.StringRes(Res.string.error_todo_save_failed)
    TodoError.DELETE_FAILED -> UiText.StringRes(Res.string.error_todo_delete_failed)
    TodoError.UNKNOWN -> UiText.StringRes(Res.string.error_unknown)
}
