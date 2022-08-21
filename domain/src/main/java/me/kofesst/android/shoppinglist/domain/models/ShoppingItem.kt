package me.kofesst.android.shoppinglist.domain.models

data class ShoppingItem(
    var name: String = "",
    var amount: Int = 1,
    var done: Boolean = false,
    var checked: Boolean = false,
    var totalCost: Double? = null,
)
