package me.kofesst.android.shoppinglist.domain.usecases.validation

class ValidateForInteger {
    operator fun invoke(value: String) = if (value.toIntOrNull() == null) {
        ValidationResult.InvalidInteger
    } else {
        ValidationResult.Success
    }
}