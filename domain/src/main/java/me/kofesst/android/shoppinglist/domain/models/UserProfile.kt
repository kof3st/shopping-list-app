package me.kofesst.android.shoppinglist.domain.models

data class UserProfile(
    val uid: String,
    var firstName: String = "",
    var lastName: String = ""
)