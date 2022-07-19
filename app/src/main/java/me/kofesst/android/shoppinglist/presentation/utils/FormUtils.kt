package me.kofesst.android.shoppinglist.presentation.utils

import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidationResult
import me.kofesst.android.shoppinglist.domain.utils.AuthResult

val ValidationResult.errorMessage: UiText?
    get() = when (this) {
        ValidationResult.EmptyField -> emptyFieldError
        ValidationResult.InvalidInteger -> invalidIntError
        ValidationResult.NotInRange -> notInRangeError
        ValidationResult.LongLength -> longLengthError
        ValidationResult.SmallLength -> smallLengthError
        ValidationResult.InvalidEmail -> invalidEmailError
        ValidationResult.PasswordContainsSpace -> passwordContainsSpaceError
        ValidationResult.PasswordNeedDigit -> passwordNeedDigitError
        ValidationResult.PasswordNeedLetters -> passwordNeedLetterError
        ValidationResult.Success -> null
    }

val AuthResult.Failed.errorMessage: UiText
    get() = when (this) {
        AuthResult.Failed.EmailAlreadyExists -> emailAlreadyExistsError
        AuthResult.Failed.IncorrectPassword -> incorrectPasswordError
        AuthResult.Failed.InvalidUser -> invalidUserError
        is AuthResult.Failed.Unexpected -> UiText.Static(exception.toString())
    }