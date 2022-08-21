package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class GetOwnedLists(private val repository: ShoppingListRepository) {
    suspend operator fun invoke() =
        repository.getOwnedLists()
}