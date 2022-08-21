package me.kofesst.android.shoppinglist.data.models

import me.kofesst.android.shoppinglist.domain.models.ShoppingItem

data class ShoppingItemDto(
    val name: String = "",
    val amount: Int = 1,
    val done: Boolean = false,
    val checked: Boolean = false,
    val totalCost: Double? = null
) {
    companion object {
        fun fromDomain(domain: ShoppingItem) = domain.run {
            ShoppingItemDto(
                name = name,
                amount = amount,
                done = done,
                checked = checked,
                totalCost = totalCost
            )
        }
    }

    fun toDomain() = ShoppingItem(
        name = name,
        amount = amount,
        done = done,
        checked = checked,
        totalCost = totalCost
    )
}