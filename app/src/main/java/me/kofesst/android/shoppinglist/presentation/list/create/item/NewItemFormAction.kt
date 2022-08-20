package me.kofesst.android.shoppinglist.presentation.list.create.item

sealed class NewItemFormAction {
    data class NameChanged(val name: String) : NewItemFormAction()
    data class AmountChanged(val amount: Int?) : NewItemFormAction()

    object Submit : NewItemFormAction()
}
