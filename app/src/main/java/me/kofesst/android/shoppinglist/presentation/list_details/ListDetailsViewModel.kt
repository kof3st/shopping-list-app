package me.kofesst.android.shoppinglist.presentation.list_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.usecases.UseCases
import me.kofesst.android.shoppinglist.presentation.utils.LoadingState
import javax.inject.Inject

@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _detailsState = mutableStateOf<LoadingState<ShoppingList>>(LoadingState.Idle())
    val detailsState: State<LoadingState<ShoppingList>> = _detailsState

    fun loadDetails(listId: String) {
        viewModelScope.launch {
            _detailsState.value = LoadingState.Loading()
            try {
                _detailsState.value = LoadingState.Loaded(
                    useCases.getList(listId)
                )
            } catch (exception: Exception) {
                _detailsState.value = LoadingState.Failed(exception)
            }
        }
    }

    fun deleteList(onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            if (detailsState.value is LoadingState.Loaded) {
                val loaded = detailsState.value as LoadingState.Loaded
                val list = loaded.value
                if (list != null) {
                    useCases.deleteList(list.id)
                }
            }
            onComplete()
        }
    }
}