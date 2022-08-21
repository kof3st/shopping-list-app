package me.kofesst.android.shoppinglist.domain.models

import java.util.*

data class ShoppingList(
    val id: String = UUID.randomUUID().toString(),
    val items: MutableList<ShoppingItem> = mutableListOf(),
    val author: UserProfile = UserProfile(uid = ""),
    var done: Boolean = false,
    var doneBy: String = "",
    var doneAt: Long = 0
)
