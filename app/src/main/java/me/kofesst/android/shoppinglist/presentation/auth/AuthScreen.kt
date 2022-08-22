package me.kofesst.android.shoppinglist.presentation.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.Flow
import me.kofesst.android.shoppinglist.domain.utils.AuthResult
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.MainViewModel
import me.kofesst.android.shoppinglist.presentation.screen.Screen
import me.kofesst.android.shoppinglist.presentation.utils.AppText
import me.kofesst.android.shoppinglist.presentation.utils.UiText
import me.kofesst.android.shoppinglist.presentation.utils.activity
import me.kofesst.android.shoppinglist.presentation.utils.errorMessage
import me.kofesst.android.shoppinglist.ui.components.Buttons
import me.kofesst.android.shoppinglist.ui.components.LoadingHandler
import me.kofesst.android.shoppinglist.ui.components.TextFields

@Suppress("OPT_IN_IS_NOT_ENABLED")
class AuthScreen(
    routeName: String
) : Screen<AuthViewModel>(
    routeName = routeName
) {
    override val viewModelProducer:
            @Composable (NavHostController, NavBackStackEntry) -> AuthViewModel
        get() = { _, _ -> hiltViewModel() }

    override val content:
            @Composable BoxScope.(NavBackStackEntry, AuthViewModel, Modifier) -> Unit
        get() = { _, viewModel, modifier ->
            LaunchedEffect(Unit) {
                viewModel.tryRestoreSession()
            }
            val screenState = viewModel.screenState
            val formState = viewModel.formState
            AuthForm(
                screenState = screenState,
                onScreenStateToggle = {
                    viewModel.toggleScreenState()
                },
                formState = formState,
                onFormAction = { action ->
                    viewModel.onFormAction(action)
                },
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(20.dp)
                    .animateContentSize()
            )
            LoadingHandler(
                viewModel = viewModel
            )
            val context = LocalContext.current
            val mainViewModel = hiltViewModel<MainViewModel>(
                viewModelStoreOwner = context.activity!!
            )
            val appState = LocalAppState.current
            FormResultListener(
                result = viewModel.formResult,
                onSuccess = {
                    mainViewModel.onSignIn()
                    appState.navController.navigate(
                        route = Home.routeName
                    ) {
                        popUpTo(this@AuthScreen.routeName) {
                            inclusive = true
                        }
                    }
                },
                onFailed = {
                    appState.showSnackbar(
                        message = it.errorMessage(context = context)
                    )
                }
            )
        }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun AuthForm(
        screenState: AuthScreenState,
        onScreenStateToggle: () -> Unit,
        formState: AuthFormState,
        onFormAction: (AuthFormAction) -> Unit,
        modifier: Modifier = Modifier,
        contentSpacing: Dp = 7.dp,
        formAndActionsSpacing: Dp = 10.dp
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = contentSpacing,
                alignment = Alignment.CenterVertically
            ),
            modifier = modifier
        ) {
            EmailField(
                email = formState.email,
                errorMessage = formState.emailError,
                onEmailChange = {
                    onFormAction(
                        AuthFormAction.EmailChanged(it)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                password = formState.password,
                errorMessage = formState.passwordError,
                onPasswordChange = {
                    onFormAction(
                        AuthFormAction.PasswordChanged(it)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (screenState is AuthScreenState.Register) {
                RegistrationFields(
                    firstName = formState.firstName,
                    firstNameError = formState.firstNameError,
                    onFirstNameChange = {
                        onFormAction(
                            AuthFormAction.FirstNameChanged(it)
                        )
                    },
                    lastName = formState.lastName,
                    lastNameError = formState.lastNameError,
                    onLastNameChange = {
                        onFormAction(
                            AuthFormAction.LastNameChanged(it)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(
                modifier = Modifier.height(formAndActionsSpacing)
            )
            AuthFormActions(
                screenState = screenState,
                onScreenStateToggle = onScreenStateToggle,
                onSubmit = {
                    keyboardController?.hide()
                    onFormAction(
                        AuthFormAction.Submit
                    )
                }
            )
        }
    }

    @Composable
    private fun EmailField(
        email: String,
        errorMessage: UiText?,
        onEmailChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TextFields.OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            errorMessage = errorMessage?.invoke(),
            textStyle = MaterialTheme.typography.body1,
            label = AppText.Label.emailLabel(),
            modifier = modifier
        )
    }

    @Composable
    private fun PasswordField(
        password: String,
        errorMessage: UiText?,
        onPasswordChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TextFields.OutlinedPasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            errorMessage = errorMessage?.invoke(),
            textStyle = MaterialTheme.typography.body1,
            label = AppText.Label.passwordLabel(),
            modifier = modifier
        )
    }

    @Composable
    private fun RegistrationFields(
        firstName: String,
        firstNameError: UiText?,
        onFirstNameChange: (String) -> Unit,
        lastName: String,
        lastNameError: UiText?,
        onLastNameChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        FirstNameField(
            firstName = firstName,
            firstNameError = firstNameError,
            onFirstNameChange = onFirstNameChange,
            modifier = modifier
        )
        LastNameField(
            lastName = lastName,
            lastNameError = lastNameError,
            onLastNameChange = onLastNameChange,
            modifier = modifier
        )
    }

    @Composable
    private fun FirstNameField(
        firstName: String,
        firstNameError: UiText?,
        onFirstNameChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TextFields.OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            errorMessage = firstNameError?.invoke(),
            textStyle = MaterialTheme.typography.body1,
            label = AppText.Label.firstNameLabel(),
            modifier = modifier
        )
    }

    @Composable
    private fun LastNameField(
        lastName: String,
        lastNameError: UiText?,
        onLastNameChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TextFields.OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            errorMessage = lastNameError?.invoke(),
            textStyle = MaterialTheme.typography.body1,
            label = AppText.Label.lastNameLabel(),
            modifier = modifier
        )
    }

    @Composable
    private fun AuthFormActions(
        screenState: AuthScreenState,
        onScreenStateToggle: () -> Unit,
        onSubmit: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        AuthFormSubmitButton(
            screenState = screenState,
            onSubmit = onSubmit,
            modifier = modifier
        )
        ToggleScreenStateButton(
            screenState = screenState,
            onScreenStateToggle = onScreenStateToggle,
            modifier = modifier
        )
    }

    @Composable
    fun AuthFormSubmitButton(
        screenState: AuthScreenState,
        onSubmit: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Buttons.Button(
            text = when (screenState) {
                is AuthScreenState.LogIn -> {
                    AppText.signInActionText()
                }
                is AuthScreenState.Register -> {
                    AppText.signUpActionText()
                }
            },
            onClick = onSubmit,
            modifier = modifier
        )
    }

    @Composable
    private fun ToggleScreenStateButton(
        screenState: AuthScreenState,
        onScreenStateToggle: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Buttons.TextButton(
            text = when (screenState) {
                is AuthScreenState.LogIn -> {
                    AppText.Action.signInAction()
                }
                is AuthScreenState.Register -> {
                    AppText.Action.signUpAction()
                }
            },
            modifier = modifier,
            onClick = onScreenStateToggle
        )
    }

    @Composable
    private fun FormResultListener(
        result: Flow<AuthResult>,
        onSuccess: (AuthResult.Success) -> Unit,
        onFailed: (AuthResult.Failed) -> Unit
    ) {
        LaunchedEffect(Unit) {
            result.collect {
                when (it) {
                    is AuthResult.Success -> {
                        onSuccess(it)
                    }
                    is AuthResult.Failed -> {
                        onFailed(it)
                    }
                }
            }
        }
    }
}