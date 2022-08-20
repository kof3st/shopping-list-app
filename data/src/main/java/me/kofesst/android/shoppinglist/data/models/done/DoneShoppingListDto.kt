package me.kofesst.android.shoppinglist.data.models.done

import me.kofesst.android.shoppinglist.domain.models.UserProfile
import me.kofesst.android.shoppinglist.domain.models.done.DoneShoppingList

data class DoneShoppingListDto(
    val id: String = "",
    val items: List<DoneShoppingItemDto> = emptyList(),
    val authorUid: String = "",
    val completedAt: Long = 0,
    val completedBy: String = ""
) {
    companion object {
        fun fromDomain(domain: DoneShoppingList) = domain.run {
            DoneShoppingListDto(
                id = id,
                items = items.map { DoneShoppingItemDto.fromDomain(it) },
                authorUid = author.uid,
                completedAt = doneAt,
                completedBy = doneBy
            )
        }
    }

    fun toDomain(
        authorProfile: UserProfile
    ) = DoneShoppingList(
        id = id,
        items = items.map { it.toDomain() },
        author = authorProfile,
        doneAt = completedAt,
        doneBy = completedBy
    )
}