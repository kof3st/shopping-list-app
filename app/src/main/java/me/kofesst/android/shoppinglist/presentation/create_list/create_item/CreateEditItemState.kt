package me.kofesst.android.shoppinglist.presentation.create_list.create_item

import me.kofesst.android.shoppinglist.presentation.utils.UiText

data class CreateEditItemState(
    val name: String = "",
    val nameError: UiText? = null,
    val amountStr: String = "",
    val amountError: UiText? = null
)
