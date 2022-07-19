package me.kofesst.android.shoppinglist.data.models

import com.google.firebase.auth.FirebaseUser
import me.kofesst.android.shoppinglist.domain.models.UserProfile

data class UserProfileDto(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = ""
) {
    companion object {
        fun fromFirebase(
            user: FirebaseUser,
            firstName: String,
            lastName: String
        ) = UserProfileDto(
            uid = user.uid,
            firstName = firstName,
            lastName = lastName
        )
    }

    fun toDomain() = UserProfile(
        uid = uid,
        firstName = firstName,
        lastName = lastName
    )
}
