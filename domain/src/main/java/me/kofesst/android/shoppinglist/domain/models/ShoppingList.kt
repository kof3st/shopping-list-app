package me.kofesst.android.shoppinglist.domain.models

import java.util.*

data class ShoppingList(
    val id: String = UUID.randomUUID().toString(),
    val items: MutableList<ShoppingItem> = mutableListOf(),
    val authorUid: String = ""
)
