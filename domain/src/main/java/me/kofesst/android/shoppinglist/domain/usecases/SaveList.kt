package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class SaveList(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(list: ShoppingList) {
        repository.saveList(list)
    }
}