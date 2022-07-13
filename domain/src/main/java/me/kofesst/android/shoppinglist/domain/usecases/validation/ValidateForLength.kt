package me.kofesst.android.shoppinglist.domain.usecases.validation

class ValidateForLength {
    operator fun invoke(value: String, lengthRange: IntRange) = value.length.run {
        if (this < lengthRange.first) {
            ValidationResult.SmallLength
        } else if (this > lengthRange.last) {
            ValidationResult.LongLength
        } else {
            ValidationResult.Success
        }
    }
}