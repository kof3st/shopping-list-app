package me.kofesst.android.shoppinglist.presentation.utils

sealed class Constraints {
    class ShoppingItem private constructor() {
        companion object {
            val NAME_LENGTH_RANGE = 2..30
            val AMOUNT_RANGE = 1..30
        }
    }
}