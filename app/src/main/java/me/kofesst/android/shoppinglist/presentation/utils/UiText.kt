package me.kofesst.android.shoppinglist.presentation.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.kofesst.android.shoppinglist.R

sealed class UiText {
    var defaultFormats = listOf<Any>()

    @Composable
    abstract fun asString(vararg formats: Any): String

    data class Static(val text: String) : UiText() {
        @Composable
        override fun asString(vararg formats: Any): String =
            text.format(*(defaultFormats + formats).toTypedArray())
    }

    data class Resource(@StringRes val resId: Int) : UiText() {
        @Composable
        override fun asString(vararg formats: Any): String =
            stringResource(id = resId, *(defaultFormats + formats).toTypedArray())
    }
}

val emptyFieldError = UiText.Resource(R.string.error__empty_field)
val invalidIntError = UiText.Resource(R.string.error__invalid_int)
val notInRangeError = UiText.Resource(R.string.error__not_in_range).apply {
    this.defaultFormats = listOf(
        Constraints.ShoppingItem.AMOUNT_RANGE.first,
        Constraints.ShoppingItem.AMOUNT_RANGE.last
    )
}
val smallLengthError = UiText.Resource(R.string.error__small_length).apply {
    this.defaultFormats = listOf(
        Constraints.ShoppingItem.NAME_LENGTH_RANGE.first
    )
}
val longLengthError = UiText.Resource(R.string.error__long_length).apply {
    this.defaultFormats = listOf(
        Constraints.ShoppingItem.NAME_LENGTH_RANGE.last
    )
}

val homeScreenTitle = UiText.Resource(R.string.home_screen_title)
val shoppingListIdLabel = UiText.Resource(R.string.shopping_list_id_label)
val searchShoppingListText = UiText.Resource(R.string.search_shopping_list)
val otherActionText = UiText.Resource(R.string.other_action)
val createNewListText = UiText.Resource(R.string.create_new_list)

val newListScreenTitle = UiText.Resource(R.string.new_list_screen_title)
val addItemText = UiText.Resource(R.string.add_item)
val submitListText = UiText.Resource(R.string.submit_list)
val emptyListMessage = UiText.Resource(R.string.empty_list_message)
val listIdCopiedMessage = UiText.Resource(R.string.list_id_copied_message)
val cannotSaveEmptyListMessage = UiText.Resource(R.string.cannot_save_empty_list_message)

val createEditItemScreenTitle = UiText.Resource(R.string.create_edit_item_screen_title)
val submitItemText = UiText.Resource(R.string.submit_item)
val itemNameLabel = UiText.Resource(R.string.item_name_label)
val itemAmountLabel = UiText.Resource(R.string.item_amount_label)

val listDetailsScreenTitle = UiText.Resource(R.string.list_details_screen_title)
val nullListMessage = UiText.Resource(R.string.null_list_message)
val confirmDialogTitle = UiText.Resource(R.string.confirm_dialog_title)
val confirmDialogMessage = UiText.Resource(R.string.confirm_dialog_message)
val confirmDialogDismissText = UiText.Resource(R.string.confirm_dialog_dismiss)
val confirmDialogAcceptText = UiText.Resource(R.string.confirm_dialog_accept)