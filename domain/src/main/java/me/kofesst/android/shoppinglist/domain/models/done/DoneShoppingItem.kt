package me.kofesst.android.shoppinglist.domain.models.done

import me.kofesst.android.shoppinglist.domain.models.ShoppingItem
import me.kofesst.android.shoppinglist.domain.models.UserProfile

data class DoneShoppingItem(
    val name: String,
    val amount: Int,
    var totalCost: Double?,
    var completed: Boolean
) {
    companion object {
        fun fromDefault(default: ShoppingItem) = default.run {
            DoneShoppingItem(
                name = name,
                amount = amount,
                totalCost = null,
                completed = false
            )
        }
    }
}