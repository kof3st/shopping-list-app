package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class RegisterUser(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) = repository.register(firstName, lastName, email, password)
}