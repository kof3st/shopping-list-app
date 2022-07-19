package me.kofesst.android.shoppinglist.presentation.utils

sealed class Constraints {
    class ShoppingItem private constructor() {
        companion object {
            val NAME_LENGTH_RANGE = 2..30
            val AMOUNT_RANGE = 1..30
        }
    }

    class UserProfile private constructor() {
        companion object {
            val NAME_LENGTH_RANGE = 1..20
            val PASSWORD_LENGTH_RANGE = 6..20
        }
    }
}