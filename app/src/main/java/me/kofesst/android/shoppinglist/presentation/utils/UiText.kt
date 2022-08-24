package me.kofesst.android.shoppinglist.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.kofesst.android.shoppinglist.R

sealed class UiText {
    var defaultFormats = listOf<Any>()

    @Composable
    protected abstract fun asString(vararg formats: Any): String

    protected abstract fun asString(context: Context, vararg formats: Any): String

    @Composable
    operator fun invoke(vararg formats: Any) =
        asString(formats = formats)

    operator fun invoke(context: Context, vararg formats: Any) =
        asString(context = context, formats = formats)

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

sealed class AppText {
    object Error : AppText() {
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
    }
    object Title : AppText() {
        val authScreenTitle = UiText.Resource(R.string.title__auth_screen)
        val homeScreenTitle = UiText.Resource(R.string.title__home_screen)
        val listsScreenTitle = UiText.Resource(R.string.title__lists_screen)
        val newListScreenTitle = UiText.Resource(R.string.title__new_list_screen)
        val createEditItemScreenTitle = UiText.Resource(R.string.title__create_edit_item_screen)
        val listDetailsScreenTitle = UiText.Resource(R.string.title__list_details_screen)
        val confirmDialogTitle = UiText.Resource(R.string.title__confirm_dialog)
        val shareListTitle = UiText.Resource(R.string.title__share_list)
    }
    object Action : AppText() {
        val searchShoppingListAction = UiText.Resource(R.string.action__search_shopping_list)
        val clearSessionAction = UiText.Resource(R.string.action__clear_session)
        val createNewListAction = UiText.Resource(R.string.action__create_new_list)
        val addNewListItemAction = UiText.Resource(R.string.action__add_item)
        val submitListAction = UiText.Resource(R.string.action__submit_list)
        val submitItemAction = UiText.Resource(R.string.action__submit_item)
        val confirmDialogDismissAction = UiText.Resource(R.string.action__confirm_dialog_dismiss)
        val confirmDialogAcceptAction = UiText.Resource(R.string.action__confirm_dialog_accept)
        val signInAction = UiText.Resource(R.string.action__sign_in)
        val signUpAction = UiText.Resource(R.string.action__sign_up)
        val copyListIdAction = UiText.Resource(R.string.action__copy_list_id)
        val goHomeAction = UiText.Resource(R.string.action__go_home)
        val showChangedListAction = UiText.Resource(R.string.action__show_changed_list)
    }
    object Label : AppText() {
        val shoppingListIdLabel = UiText.Resource(R.string.label__shopping_list_id)
        val emailLabel = UiText.Resource(R.string.label__email)
        val passwordLabel = UiText.Resource(R.string.label__password)
        val firstNameLabel = UiText.Resource(R.string.label__first_name)
        val lastNameLabel = UiText.Resource(R.string.label__last_name)
        val itemNameLabel = UiText.Resource(R.string.label__item_name)
        val itemAmountLabel = UiText.Resource(R.string.label__item_amount)
    }
    object Case : AppText() {
        val hoursFirstCase = UiText.Resource(R.string.case__hours_first)
        val hoursSecondCase = UiText.Resource(R.string.case__hours_second)
        val hoursThirdCase = UiText.Resource(R.string.case__hours_third)
        val januaryCase = UiText.Resource(R.string.case__january)
        val februaryCase = UiText.Resource(R.string.case__february)
        val marchCase = UiText.Resource(R.string.case__march)
        val aprilCase = UiText.Resource(R.string.case__april)
        val mayCase = UiText.Resource(R.string.case__may)
        val juneCase = UiText.Resource(R.string.case__june)
        val julyCase = UiText.Resource(R.string.case__july)
        val augustCase = UiText.Resource(R.string.case__august)
        val septemberCase = UiText.Resource(R.string.case__september)
        val octoberCase = UiText.Resource(R.string.case__october)
        val novemberCase = UiText.Resource(R.string.case__november)
        val decemberCase = UiText.Resource(R.string.case__december)
    }
    object Format : AppText() {
        val dateFormat = UiText.Resource(R.string.format__date)
        val timeFormat = UiText.Resource(R.string.format__time)
    }
    object Toast : AppText() {
        val listIdCopiedToast = UiText.Resource(R.string.toast__list_id_copied)
        val cannotSaveEmptyListToast = UiText.Resource(R.string.toast__cannot_save_empty_list)
        val queryListIdIsEmptyToast = UiText.Resource(R.string.toast__query_list_id_is_empty)
        val listChangedToast = UiText.Resource(R.string.toast__list_changed)
    }

    companion object {
        val otherActionText = UiText.Resource(R.string.other_action)
        val activeListsSectionText = UiText.Resource(R.string.active_lists_section)
        val doneListsSectionText = UiText.Resource(R.string.done_lists_section)
        val activeListText = UiText.Resource(R.string.active_list)
        val doneListText = UiText.Resource(R.string.done_list)
        val listItemsText = UiText.Resource(R.string.list_items)
        val listDoneByText = UiText.Resource(R.string.list_done_by)
        val listDoneAtText = UiText.Resource(R.string.list_done_at)
        val emptyListText = UiText.Resource(R.string.empty_list)
        val listSentFromText = UiText.Resource(R.string.list_sent_from)
        val emptyListsSectionText = UiText.Resource(R.string.empty_lists_section)
        val nullListText = UiText.Resource(R.string.null_list)
        val confirmDialogMessageText = UiText.Resource(R.string.confirm_dialog_message)
        val signUpActionText = UiText.Resource(R.string.sign_up_action)
        val signInActionText = UiText.Resource(R.string.sign_in_action)
        val listCreatedText = UiText.Resource(R.string.list_created)
        val checkingForSessionText = UiText.Resource(R.string.checking_for_session)
    }
}