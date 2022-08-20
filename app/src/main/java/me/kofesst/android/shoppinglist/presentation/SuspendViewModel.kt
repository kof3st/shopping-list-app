package me.kofesst.android.shoppinglist.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class SuspendViewModel : ViewModel() {
    private val _loadingState = mutableStateOf(false)
    val loadingState: State<Boolean> get() = _loadingState

    protected fun runSuspend(
        afterRun: () -> Unit = {},
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            _loadingState.value = true
            block()
            _loadingState.value = false
            afterRun()
        }
    }
}