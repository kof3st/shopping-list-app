package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class DeleteList(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(id: String) =
        repository.deleteList(id)
}