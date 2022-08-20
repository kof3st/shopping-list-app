package me.kofesst.android.shoppinglist.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.kofesst.android.shoppinglist.R

sealed class UiText {
    var defaultFormats = listOf<Any>()

    @Composable
    abstract fun asString(vararg formats: Any): String

    abstract fun asString(context: Context, vararg formats: Any): String

    data class Static(val text: String) : UiText() {
        @Composable
        override fun asString(vararg formats: Any): String =
            text.format(*(defaultFormats + formats).toTypedArray())

        override fun asString(context: Context, vararg formats: Any): String =
            text.format(*(defaultFormats + formats).toTypedArray())
    }

    data class Resource(@StringRes val resId: Int) : UiText() {
        @Composable
        override fun asString(vararg formats: Any): String =
            stringResource(id = resId, *(defaultFormats + formats).toTypedArray())

        override fun asString(context: Context, vararg formats: Any): String =
            context.resources.getString(resId, *(defaultFormats + formats).toTypedArray())
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
val invalidEmailError = UiText.Resource(R.string.error__invalid_email)
val passwordContainsSpaceError = UiText.Resource(R.string.error__password_contains_space)
val passwordNeedDigitError = UiText.Resource(R.string.error__password_need_digit)
val passwordNeedLetterError = UiText.Resource(R.string.error__password_need_letter)
val emailAlreadyExistsError = UiText.Resource(R.string.error__email_already_exists)
val incorrectPasswordError = UiText.Resource(R.string.error__incorrect_password)
val invalidUserError = UiText.Resource(R.string.error__invalid_user)

val homeScreenTitle = UiText.Resource(R.string.home_screen_title)
val clearSessionText = UiText.Resource(R.string.clear_session)
val shoppingListIdLabel = UiText.Resource(R.string.shopping_list_id_label)
val searchShoppingListText = UiText.Resource(R.string.search_shopping_list)
val otherActionText = UiText.Resource(R.string.other_action)
val createNewListText = UiText.Resource(R.string.create_new_list)
val activeListsSectionText = UiText.Resource(R.string.active_lists_section)
val doneListsSectionText = UiText.Resource(R.string.done_lists_section)
val activeListText = UiText.Resource(R.string.active_list)
val doneListText = UiText.Resource(R.string.done_list)
val listItemsText = UiText.Resource(R.string.list_items)
val listDoneByText = UiText.Resource(R.string.list_done_by)
val listDoneAtText = UiText.Resource(R.string.list_done_at)
val listsScreenTitle = UiText.Resource(R.string.lists_screen_title)

val hoursFirstCase = UiText.Resource(R.string.hours_first_case)
val hoursSecondCase = UiText.Resource(R.string.hours_second_case)
val hoursThirdCase = UiText.Resource(R.string.hours_third_case)
val dateFormat = UiText.Resource(R.string.date_format)
val timeFormat = UiText.Resource(R.string.time_format)

val januaryCase = UiText.Resource(R.string.january_case)
val februaryCase = UiText.Resource(R.string.february_case)
val marchCase = UiText.Resource(R.string.march_case)
val aprilCase = UiText.Resource(R.string.april_case)
val mayCase = UiText.Resource(R.string.may_case)
val juneCase = UiText.Resource(R.string.june_case)
val julyCase = UiText.Resource(R.string.july_case)
val augustCase = UiText.Resource(R.string.august_case)
val septemberCase = UiText.Resource(R.string.september_case)
val octoberCase = UiText.Resource(R.string.october_case)
val novemberCase = UiText.Resource(R.string.november_case)
val decemberCase = UiText.Resource(R.string.december_case)

val newListScreenTitle = UiText.Resource(R.string.new_list_screen_title)
val addItemText = UiText.Resource(R.string.add_item)
val submitListText = UiText.Resource(R.string.submit_list)
val emptyListMessage = UiText.Resource(R.string.empty_list_message)
val listIdCopiedMessage = UiText.Resource(R.string.list_id_copied_message)
val cannotSaveEmptyListMessage = UiText.Resource(R.string.cannot_save_empty_list_message)
val queryListIdIsEmptyMessage = UiText.Resource(R.string.query_list_id_is_empty_message)
val listSentFromText = UiText.Resource(R.string.list_sent_from)
val emptyListsSectionText = UiText.Resource(R.string.empty_lists_section)

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

val emailLabel = UiText.Resource(R.string.email_label)
val passwordLabel = UiText.Resource(R.string.password_label)
val firstNameLabel = UiText.Resource(R.string.first_name_label)
val lastNameLabel = UiText.Resource(R.string.last_name_label)
val logInText = UiText.Resource(R.string.log_in)
val registerActionText = UiText.Resource(R.string.register_action)
val registerText = UiText.Resource(R.string.register)
val logInActionText = UiText.Resource(R.string.log_in_action)

val listCreatedText = UiText.Resource(R.string.list_created)
val copyListIdActionText = UiText.Resource(R.string.copy_list_id_action)
val shareListTitle = UiText.Resource(R.string.share_list_title)
val goHomeActionText = UiText.Resource(R.string.go_home_action)