package me.kofesst.android.shoppinglist.data.models

import me.kofesst.android.shoppinglist.domain.models.ShoppingItem

data class ShoppingItemDto(
    val name: String = "",
    val amount: Int = 1
) {
    companion object {
        fun fromDomain(domain: ShoppingItem) = domain.run {
            ShoppingItemDto(
                name = name,
                amount = amount
            )
        }
    }

    fun toDomain() = ShoppingItem(
        name = name,
        amount = amount
    )
}