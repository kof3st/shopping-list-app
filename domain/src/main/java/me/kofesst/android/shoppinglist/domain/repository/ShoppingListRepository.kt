package me.kofesst.android.shoppinglist.domain.repository

import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.models.UserProfile
import me.kofesst.android.shoppinglist.domain.models.done.DoneShoppingList
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

    suspend fun getLoggedUserUid(): String?
    suspend fun getLoggedUserProfile(): UserProfile?

    suspend fun saveSession(
        email: String,
        password: String
    )

    suspend fun restoreSession(): Pair<String, String>?
    suspend fun clearSession()

    suspend fun saveList(list: ShoppingList)
    suspend fun getList(id: String): ShoppingList?
    suspend fun completeList(list: DoneShoppingList)

    suspend fun getSelfActiveLists(): List<ShoppingList>
    suspend fun getSelfDoneLists(): List<DoneShoppingList>
}