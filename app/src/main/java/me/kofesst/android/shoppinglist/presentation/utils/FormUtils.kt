package me.kofesst.android.shoppinglist.presentation.utils

import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidationResult
import me.kofesst.android.shoppinglist.domain.utils.AuthResult

val ValidationResult.errorMessage: UiText?
    get() = when (this) {
        ValidationResult.NullField -> AppText.Error.emptyFieldError
        ValidationResult.EmptyField -> AppText.Error.emptyFieldError
        ValidationResult.InvalidInteger -> AppText.Error.invalidIntError
        ValidationResult.NotInRange -> AppText.Error.notInRangeError
        ValidationResult.LongLength -> AppText.Error.longLengthError
        ValidationResult.SmallLength -> AppText.Error.smallLengthError
        ValidationResult.InvalidEmail -> AppText.Error.invalidEmailError
        ValidationResult.PasswordContainsSpace -> AppText.Error.passwordContainsSpaceError
        ValidationResult.PasswordNeedDigit -> AppText.Error.passwordNeedDigitError
        ValidationResult.PasswordNeedLetters -> AppText.Error.passwordNeedLetterError
        ValidationResult.Success -> null
    }

val AuthResult.Failed.errorMessage: UiText
    get() = when (this) {
        AuthResult.Failed.EmailAlreadyExists -> AppText.Error.emailAlreadyExistsError
        AuthResult.Failed.IncorrectPassword -> AppText.Error.incorrectPasswordError
        AuthResult.Failed.InvalidUser -> AppText.Error.invalidUserError
        is AuthResult.Failed.Unexpected -> UiText.Static(exception.toString())
    }