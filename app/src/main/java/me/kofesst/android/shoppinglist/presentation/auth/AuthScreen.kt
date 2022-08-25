package me.kofesst.android.shoppinglist.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import me.kofesst.android.shoppinglist.R
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
import me.kofesst.android.shoppinglist.ui.components.LottieMessage
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
            AuthFormContent(
                screenState = screenState,
                onScreenStateToggle = {
                    viewModel.toggleScreenState()
                },
                formState = formState,
                onFormAction = { action ->
                    viewModel.onFormAction(action)
                },
                modifier = modifier.fillMaxSize()
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

            LoadingHandler(
                viewModel = viewModel
            )

            val sessionCheckState by viewModel.sessionCheckState
            AnimatedVisibility(
                visible = sessionCheckState,
                enter = EnterTransition.None,
                exit = fadeOut(
                    animationSpec = keyframes {
                        this.durationMillis = 1000
                    }
                )
            ) {
                AuthSessionSplashScreen()
            }
        }

    @Composable
    private fun AuthSessionSplashScreen() {
        LottieMessage(
            lottieRes = R.raw.shopping_cart_lottie,
            message = AppText.checkingForSessionText(),
            shouldFillBackground = true
        )
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun AuthFormContent(
        formState: AuthFormState,
        onFormAction: (AuthFormAction) -> Unit,
        screenState: AuthScreenState,
        onScreenStateToggle: () -> Unit,
        modifier: Modifier = Modifier,
        contentPadding: Dp = 30.dp
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Box(
            modifier = modifier.padding(contentPadding)
        ) {
            AuthForm(
                formState = formState,
                onFormAction = onFormAction,
                screenState = screenState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )
            AuthFormActions(
                screenState = screenState,
                onScreenStateToggle = onScreenStateToggle,
                onSubmit = {
                    keyboardController?.hide()
                    onFormAction(
                        AuthFormAction.Submit
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }

    @Composable
    private fun AuthFormActions(
        screenState: AuthScreenState,
        onScreenStateToggle: () -> Unit,
        onSubmit: () -> Unit,
        modifier: Modifier = Modifier,
        actionsSpacing: Dp = 20.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            AuthFormSubmitButton(
                screenState = screenState,
                onSubmit = onSubmit,
                modifier = modifier
            )
            Spacer(
                modifier = Modifier.height(actionsSpacing)
            )
            ToggleScreenStateAction(
                screenState = screenState,
                onScreenStateToggle = onScreenStateToggle,
                modifier = modifier
            )
        }
    }

    @Composable
    private fun AuthForm(
        formState: AuthFormState,
        onFormAction: (AuthFormAction) -> Unit,
        screenState: AuthScreenState,
        modifier: Modifier = Modifier,
        headerPadding: Dp = 30.dp,
        fieldsSpacing: Dp = 15.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.animateContentSize()
        ) {
            Text(
                text = AppText.Title.authScreenTitle(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(
                modifier = Modifier.height(headerPadding)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(fieldsSpacing),
                modifier = Modifier.fillMaxWidth()
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
            }
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
            textStyle = MaterialTheme.typography.bodyLarge,
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
            textStyle = MaterialTheme.typography.bodyLarge,
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
            textStyle = MaterialTheme.typography.bodyLarge,
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
            textStyle = MaterialTheme.typography.bodyLarge,
            label = AppText.Label.lastNameLabel(),
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
                    AppText.Action.signInAction()
                }
                is AuthScreenState.Register -> {
                    AppText.Action.signUpAction()
                }
            },
            onClick = onSubmit,
            modifier = modifier
        )
    }

    @Composable
    private fun ToggleScreenStateAction(
        screenState: AuthScreenState,
        onScreenStateToggle: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Text(
                text = when (screenState) {
                    is AuthScreenState.LogIn -> {
                        AppText.signInActionText()
                    }
                    is AuthScreenState.Register -> {
                        AppText.signUpActionText()
                    }
                },
                style = MaterialTheme.typography.bodyMedium
            )
            Buttons.TextButton(
                text = when (screenState) {
                    is AuthScreenState.LogIn -> {
                        AppText.Action.signUpAction()
                    }
                    is AuthScreenState.Register -> {
                        AppText.Action.signInAction()
                    }
                },
                onClick = onScreenStateToggle
            )
        }
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