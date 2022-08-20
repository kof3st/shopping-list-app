package me.kofesst.android.shoppinglist.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import me.kofesst.android.shoppinglist.domain.usecases.UseCases
import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidationResult
import me.kofesst.android.shoppinglist.domain.utils.AuthResult
import me.kofesst.android.shoppinglist.presentation.SuspendViewModel
import me.kofesst.android.shoppinglist.presentation.utils.Constraints
import me.kofesst.android.shoppinglist.presentation.utils.errorMessage
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCases: UseCases
) : SuspendViewModel() {
    var formState by mutableStateOf(AuthFormState())
    var screenState by mutableStateOf<AuthScreenState>(AuthScreenState.LogIn)

    private val validationChannel = Channel<AuthResult>()
    val formResult = validationChannel.receiveAsFlow()

    fun tryRestoreSession() {
        var session: Pair<String, String>? = null
        runSuspend(
            afterRun = {
                session?.run {
                    formState = formState.copy(
                        email = this.first,
                        password = this.second
                    )
                    onSubmit()
                }
            }
        ) {
            session = useCases.restoreSession()
        }
    }

    fun toggleScreenState() {
        screenState = screenState.opposite()
    }

    fun onFormAction(action: AuthFormAction) {
        when (action) {
            is AuthFormAction.EmailChanged -> {
                formState = formState.copy(email = action.email)
            }
            is AuthFormAction.FirstNameChanged -> {
                formState = formState.copy(firstName = action.firstName)
            }
            is AuthFormAction.LastNameChanged -> {
                formState = formState.copy(lastName = action.lastName)
            }
            is AuthFormAction.PasswordChanged -> {
                formState = formState.copy(password = action.password)
            }
            AuthFormAction.Submit -> onSubmit()
        }
    }

    private fun onSubmit() {
        formState = formState.run {
            this.copy(
                email = email.trim(),
                password = password.trim(),
                firstName = firstName.trim(),
                lastName = lastName.trim()
            )
        }

        val emailResult = useCases.validateForEmail(formState.email)
        val passwordResult = useCases.validateForLength(
            value = formState.password,
            lengthRange = Constraints.UserProfile.PASSWORD_LENGTH_RANGE
        ) + useCases.validateForPassword(
            value = formState.password
        )
        val firstNameResult = if (screenState is AuthScreenState.Register) {
            useCases.validateForLength(
                value = formState.firstName,
                lengthRange = Constraints.UserProfile.NAME_LENGTH_RANGE
            )
        } else {
            ValidationResult.Success
        }
        val lastNameResult = if (screenState is AuthScreenState.Register) {
            useCases.validateForLength(
                value = formState.lastName,
                lengthRange = Constraints.UserProfile.NAME_LENGTH_RANGE
            )
        } else {
            ValidationResult.Success
        }

        formState = formState.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            firstNameError = firstNameResult.errorMessage,
            lastNameError = lastNameResult.errorMessage
        )

        val hasError = listOf(
            emailResult,
            passwordResult,
            firstNameResult,
            lastNameResult
        ).any { it !is ValidationResult.Success }

        if (!hasError) {
            runSuspend {
                val result = when (screenState) {
                    is AuthScreenState.Register -> {
                        useCases.registerUser(
                            firstName = formState.firstName,
                            lastName = formState.lastName,
                            email = formState.email,
                            password = formState.password
                        )
                    }
                    is AuthScreenState.LogIn -> {
                        useCases.logInUser(
                            email = formState.email,
                            password = formState.password
                        )
                    }
                }

                useCases.saveSession(
                    email = formState.email,
                    password = formState.password
                )
                validationChannel.send(result)
            }
        }
    }
}