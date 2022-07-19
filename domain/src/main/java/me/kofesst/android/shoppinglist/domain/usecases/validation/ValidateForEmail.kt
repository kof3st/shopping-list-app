package me.kofesst.android.shoppinglist.domain.usecases.validation

import android.util.Patterns

class ValidateForEmail {
    operator fun invoke(value: String) = if (Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
        ValidationResult.Success
    } else {
        ValidationResult.InvalidEmail
    }
}