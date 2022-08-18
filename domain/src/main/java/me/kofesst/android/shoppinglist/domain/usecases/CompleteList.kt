package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.models.done.DoneShoppingItem
import me.kofesst.android.shoppinglist.domain.models.done.DoneShoppingList
import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class CompleteList(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(list: DoneShoppingList) =
        repository.completeList(list)
}