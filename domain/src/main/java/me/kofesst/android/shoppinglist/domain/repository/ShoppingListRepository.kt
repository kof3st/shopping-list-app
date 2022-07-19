package me.kofesst.android.shoppinglist.domain.repository

import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.utils.AuthResult

interface ShoppingListRepository {
    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthResult

    suspend fun logIn(
        email: String,
        password: String
    ): AuthResult

    suspend fun saveList(list: ShoppingList)
    suspend fun getList(id: String): ShoppingList?
    suspend fun deleteList(id: String)
}