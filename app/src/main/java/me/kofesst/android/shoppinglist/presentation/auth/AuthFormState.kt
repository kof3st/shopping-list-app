package me.kofesst.android.shoppinglist.presentation.auth

import me.kofesst.android.shoppinglist.presentation.utils.UiText

data class AuthFormState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val firstName: String = "",
    val firstNameError: UiText? = null,
    val lastName: String = "",
    val lastNameError: UiText? = null
)
