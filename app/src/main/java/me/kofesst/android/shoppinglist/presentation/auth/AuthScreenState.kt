package me.kofesst.android.shoppinglist.presentation.auth

sealed class AuthScreenState {
    fun opposite() = when (this) {
        LogIn -> Register
        Register -> LogIn
    }

    object LogIn : AuthScreenState()
    object Register : AuthScreenState()
}
