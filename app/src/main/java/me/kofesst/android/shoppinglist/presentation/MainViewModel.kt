package me.kofesst.android.shoppinglist.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import me.kofesst.android.shoppinglist.domain.repository.NotificationsRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) : SuspendViewModel() {

    private val _authState = mutableStateOf(AuthState.LoggedOut)
    val authState: State<AuthState> get() = _authState

    fun onSignIn() {
        _authState.value = AuthState.LoggedIn
    }

    fun onSignOut() {
        _authState.value = AuthState.LoggedOut
    }

    fun subscribeToDatabaseChanges(onChange: suspend (String) -> Unit) {
        notificationsRepository.subscribeToDatabaseChanges(
            coroutineScope = viewModelScope,
            onChildChanged = onChange
        )
    }

    fun unsubscribeFromDatabaseChanges() {
        notificationsRepository.unsubscribeFromDatabaseChanges()
    }
}