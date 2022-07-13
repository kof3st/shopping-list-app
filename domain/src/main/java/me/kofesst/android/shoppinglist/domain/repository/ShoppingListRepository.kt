package me.kofesst.android.shoppinglist.domain.repository

import me.kofesst.android.shoppinglist.domain.models.ShoppingList

interface ShoppingListRepository {
    suspend fun saveList(list: ShoppingList)
    suspend fun getList(id: String): ShoppingList?
    suspend fun deleteList(id: String)
}