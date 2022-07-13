package me.kofesst.android.shoppinglist.presentation.create_list.create_item

sealed class CreateEditItemAction {
    data class NameChanged(val name: String) : CreateEditItemAction()
    data class AmountChanged(val amountStr: String) : CreateEditItemAction()

    object Submit : CreateEditItemAction()
}
