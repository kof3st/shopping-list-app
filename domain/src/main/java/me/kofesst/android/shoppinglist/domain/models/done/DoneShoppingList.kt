package me.kofesst.android.shoppinglist.domain.models.done

import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.models.UserProfile

data class DoneShoppingList(
    val id: String,
    val items: List<DoneShoppingItem> = emptyList(),
    val author: UserProfile = UserProfile(uid = ""),
    val completedAt: Long
) {
    companion object {
        fun fromList(
            list: ShoppingList,
            items: List<DoneShoppingItem>,
            completedAt: Long
        ) = list.run {
            DoneShoppingList(
                id = id,
                items = items,
                author = author,
                completedAt = completedAt
            )
        }
    }

    val totalCost: Double =
        items.sumOf { it.totalCost ?: 0.0 }
}
