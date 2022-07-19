package me.kofesst.android.shoppinglist.domain.utils

import me.kofesst.android.shoppinglist.domain.models.UserProfile

sealed class AuthResult {
    sealed class Success(val profile: UserProfile) : AuthResult() {
        class Registered(profile: UserProfile) : Success(profile)
        class LoggedIn(profile: UserProfile) : Success(profile)
    }

    sealed class Failed : AuthResult() {
        object EmailAlreadyExists : Failed()
        object InvalidUser : Failed()
        object IncorrectPassword : Failed()
        data class Unexpected(val exception: Exception) : Failed()
    }
}