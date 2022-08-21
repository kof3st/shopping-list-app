package me.kofesst.android.shoppinglist.presentation.list.create.item

import me.kofesst.android.shoppinglist.presentation.utils.UiText

data class NewItemFormState(
    val name: String = "",
    val nameError: UiText? = null,
    val amount: Int? = 1,
    val amountError: UiText? = null
)
