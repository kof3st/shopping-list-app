package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class GetSelfActiveLists(private val repository: ShoppingListRepository) {
    suspend operator fun invoke() =
        repository.getSelfActiveLists()
}