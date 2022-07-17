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