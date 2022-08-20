package me.kofesst.android.shoppinglist.presentation.list.create.item

import me.kofesst.android.shoppinglist.domain.models.ShoppingItem

sealed class NewItemFormResult {
    data class Success(val item: ShoppingItem) : NewItemFormResult()
}
