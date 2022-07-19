package me.kofesst.android.shoppinglist.domain.usecases.validation

class ValidateForPassword {
    operator fun invoke(value: String) = if (!value.contains(Regex("\\d"))) {
        ValidationResult.PasswordNeedDigit
    } else if (!value.contains(Regex("[а-яА-Яa-zA-Z]"))) {
        ValidationResult.PasswordNeedLetters
    } else if (value.contains(Regex("\\s"))) {
        ValidationResult.PasswordContainsSpace
    } else {
        ValidationResult.Success
    }
}