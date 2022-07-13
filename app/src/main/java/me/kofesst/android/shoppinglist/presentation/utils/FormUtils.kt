package me.kofesst.android.shoppinglist.presentation.utils

import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidationResult

val ValidationResult.errorMessage: UiText?
    get() = when (this) {
        ValidationResult.EmptyField -> emptyFieldError
        ValidationResult.InvalidInteger -> invalidIntError
        ValidationResult.NotInRange -> notInRangeError
        ValidationResult.LongLength -> longLengthError
        ValidationResult.SmallLength -> smallLengthError
        ValidationResult.Success -> null
    }