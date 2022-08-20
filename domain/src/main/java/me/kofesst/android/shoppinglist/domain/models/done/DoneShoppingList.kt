package me.kofesst.android.shoppinglist.domain.models.done

import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.models.UserProfile

data class DoneShoppingList(
    val id: String,
    val items: List<DoneShoppingItem> = emptyList(),
    val author: UserProfile = UserProfile(uid = ""),
    val doneAt: Long,
    val doneBy: String
) {
    companion object {
        fun fromList(
            list: ShoppingList,
            items: List<DoneShoppingItem>,
            doneAt: Long,
            doneBy: String
        ) = list.run {
            DoneShoppingList(
                id = id,
                items = items,
                author = author,
                doneAt = doneAt,
                doneBy = doneBy
            )
        }
    }

    val totalCost: Double =
        items.sumOf { it.totalCost ?: 0.0 }
}
