package me.kofesst.android.shoppinglist.data.models.done

import me.kofesst.android.shoppinglist.domain.models.done.DoneShoppingItem

data class DoneShoppingItemDto(
    val name: String = "",
    val amount: Int = 0,
    val totalCost: Double? = null,
    val completed: Boolean = false
) {
    companion object {
        fun fromDomain(domain: DoneShoppingItem) = domain.run {
            DoneShoppingItemDto(
                name = name,
                amount = amount,
                totalCost = totalCost,
                completed = completed
            )
        }
    }

    fun toDomain() = DoneShoppingItem(
        name = name,
        amount = amount,
        totalCost = totalCost,
        completed = completed
    )
}