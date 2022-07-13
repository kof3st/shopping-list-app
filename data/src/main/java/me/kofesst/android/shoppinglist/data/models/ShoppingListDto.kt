package me.kofesst.android.shoppinglist.data.models

import me.kofesst.android.shoppinglist.domain.models.ShoppingList

data class ShoppingListDto(
    val id: String = "",
    val items: List<ShoppingItemDto> = listOf(),
    val completed: Boolean = false
) {
    companion object {
        fun fromDomain(domain: ShoppingList) = domain.run {
            ShoppingListDto(
                id = id,
                items = items.map { ShoppingItemDto.fromDomain(it) },
                completed = completed
            )
        }
    }

    fun toDomain() = ShoppingList(
        id = id,
        items = items.map { it.toDomain() }.toMutableList(),
        completed = completed
    )
}