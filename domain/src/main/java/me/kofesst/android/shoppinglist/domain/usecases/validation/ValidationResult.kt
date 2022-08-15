package me.kofesst.android.shoppinglist.domain.usecases.validation

sealed class ValidationResult {
    object NullField : ValidationResult()
    object EmptyField : ValidationResult()
    object SmallLength : ValidationResult()
    object LongLength : ValidationResult()
    object InvalidInteger : ValidationResult()
    object NotInRange : ValidationResult()
    object InvalidEmail : ValidationResult()
    object PasswordNeedDigit : ValidationResult()
    object PasswordNeedLetters : ValidationResult()
    object PasswordContainsSpace : ValidationResult()

    object Success : ValidationResult()

    operator fun plus(other: ValidationResult): ValidationResult {
        if (this is Success) return other
        return this
    }
}
