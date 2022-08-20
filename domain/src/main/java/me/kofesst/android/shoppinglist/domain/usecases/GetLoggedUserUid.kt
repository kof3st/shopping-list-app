package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class GetLoggedUserUid(private val repository: ShoppingListRepository) {
    suspend operator fun invoke() =
        repository.getLoggedUserUid()
}