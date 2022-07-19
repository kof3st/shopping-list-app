package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class SaveSession(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repository.saveSession(email, password)
}