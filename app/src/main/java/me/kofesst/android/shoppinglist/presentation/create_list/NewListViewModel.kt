package me.kofesst.android.shoppinglist.presentation.create_list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.android.shoppinglist.domain.models.ShoppingItem
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.usecases.UseCases
import javax.inject.Inject

@HiltViewModel
class NewListViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    val items = mutableStateListOf<ShoppingItem>()

    fun addItem(item: ShoppingItem) {
        items.add(item)
    }

    fun updateItem(index: Int, item: ShoppingItem) {
        items[index] = item
    }

    fun removeItem(index: Int) {
        items.removeAt(index)
    }

    fun saveList(onComplete: (String) -> Unit = {}) {
        viewModelScope.launch {
            val list = ShoppingList(items = items)
            useCases.saveList(list)
            onComplete(list.id)
        }
    }
}