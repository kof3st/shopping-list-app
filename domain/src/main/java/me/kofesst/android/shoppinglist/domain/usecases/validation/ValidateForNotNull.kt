package me.kofesst.android.shoppinglist.domain.usecases.validation

class ValidateForNotNull {
    operator fun invoke(value: Any?) = if (value == null) {
        ValidationResult.NullField
    } else {
        ValidationResult.Success
    }
}