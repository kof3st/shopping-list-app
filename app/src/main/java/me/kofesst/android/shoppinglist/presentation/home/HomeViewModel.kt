package me.kofesst.android.shoppinglist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.android.shoppinglist.domain.usecases.UseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    fun clearSession(onCleared: () -> Unit) {
        viewModelScope.launch {
            useCases.clearSession()
            onCleared()
        }
    }
}