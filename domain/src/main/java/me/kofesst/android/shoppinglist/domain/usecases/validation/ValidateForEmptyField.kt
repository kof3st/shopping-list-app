package me.kofesst.android.shoppinglist.domain.usecases.validation

class ValidateForEmptyField {
    operator fun invoke(value: String) = if (value.isBlank()) {
        ValidationResult.EmptyField
    } else {
        ValidationResult.Success
    }
}