package me.kofesst.android.shoppinglist.data.models

import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.models.UserProfile

data class ShoppingListDto(
    val id: String = "",
    val items: List<ShoppingItemDto> = listOf(),
    val authorUid: String = "",
    var done: Boolean = false,
    var doneBy: String = "",
    var doneAt: Long = 0
) {
    companion object {
        fun fromDomain(domain: ShoppingList) = domain.run {
            ShoppingListDto(
                id = id,
                items = items.map { ShoppingItemDto.fromDomain(it) },
                authorUid = author.uid,
                done = done,
                doneBy = doneBy,
                doneAt = doneAt
            )
        }
    }

    fun toDomain(authorProfile: UserProfile) = ShoppingList(
        id = id,
        items = items.map { it.toDomain() }.toMutableList(),
        author = authorProfile,
        done = done,
        doneBy = doneBy,
        doneAt = doneAt
    )
}