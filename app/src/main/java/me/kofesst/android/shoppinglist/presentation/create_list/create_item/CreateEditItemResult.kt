package me.kofesst.android.shoppinglist.presentation.create_list.create_item

import me.kofesst.android.shoppinglist.domain.models.ShoppingItem

sealed class CreateEditItemResult {
    data class Success(val item: ShoppingItem) : CreateEditItemResult()
}
