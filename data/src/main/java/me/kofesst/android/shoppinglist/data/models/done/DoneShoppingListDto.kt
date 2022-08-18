package me.kofesst.android.shoppinglist.data.models.done

import me.kofesst.android.shoppinglist.domain.models.done.DoneShoppingList

data class DoneShoppingListDto(
    val id: String,
    val items: List<DoneShoppingItemDto> = emptyList(),
    val completedAt: Long
) {
    companion object {
        fun fromDomain(domain: DoneShoppingList) = domain.run {
            DoneShoppingListDto(
                id = id,
                items = items.map { DoneShoppingItemDto.fromDomain(it) },
                completedAt = completedAt
            )
        }
    }

    fun toDomain() = DoneShoppingList(
        id = id,
        items = items.map { it.toDomain() },
        completedAt = completedAt
    )
}