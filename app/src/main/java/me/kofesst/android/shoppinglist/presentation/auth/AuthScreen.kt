package me.kofesst.android.shoppinglist.presentation.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import me.kofesst.android.shoppinglist.domain.utils.AuthResult
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.Screen
import me.kofesst.android.shoppinglist.presentation.route
import me.kofesst.android.shoppinglist.presentation.utils.*
import me.kofesst.android.shoppinglist.presentation.withArgs
import me.kofesst.android.shoppinglist.ui.components.Buttons
import me.kofesst.android.shoppinglist.ui.components.TextFields

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.tryRestoreSession()
    }

    val appState = LocalAppState.current
    val navController = appState.navController

    Box(modifier = modifier) {
        val formState = viewModel.formState
        val screenState = viewModel.screenState
        AuthForm(
            formState = formState,
            screenState = screenState,
            onScreenStateToggle = {
                viewModel.toggleScreenState()
            },
            onFormAction = {
                viewModel.onFormAction(it)
            },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp)
                .animateContentSize()
        )

        val isLoading by viewModel.loadingState
        LoadingPanel(
            isLoading = isLoading,
            modifier = Modifier.align(Alignment.Center)
        )
    }

    val context = LocalContext.current
    FormResultListener(
        result = viewModel.formResult,
        onSuccess = {
            navController.navigate(
                Screen.HOME.withArgs()
            ) {
                popUpTo(Screen.AUTH.route) {
                    inclusive = true
                }
            }
        },
        onFailed = {
            appState.showSnackbar(
                message = it.errorMessage.asString(
                    context = context
                )
            )
        }
    )
}

@Composable
private fun AuthForm(
    formState: AuthFormState,
    screenState: AuthScreenState,
    modifier: Modifier = Modifier,
    onScreenStateToggle: () -> Unit = {},
    onFormAction: (AuthFormAction) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 7.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier
    ) {
        TextFields.OutlinedTextField(
            value = formState.email,
            onValueChange = {
                onFormAction(
                    AuthFormAction.EmailChanged(it)
                )
            },
            errorMessage = formState.emailError?.asString(),
            textStyle = MaterialTheme.typography.body1,
            label = emailLabel.asString(),
            modifier = Modifier.fillMaxWidth()
        )
        TextFields.OutlinedPasswordTextField(
            value = formState.password,
            onValueChange = {
                onFormAction(
                    AuthFormAction.PasswordChanged(it)
                )
            },
            errorMessage = formState.passwordError?.asString(),
            textStyle = MaterialTheme.typography.body1,
            label = passwordLabel.asString(),
            modifier = Modifier.fillMaxWidth()
        )
        if (screenState is AuthScreenState.Register) {
            TextFields.OutlinedTextField(
                value = formState.firstName,
                onValueChange = {
                    onFormAction(
                        AuthFormAction.FirstNameChanged(it)
                    )
                },
                errorMessage = formState.firstNameError?.asString(),
                textStyle = MaterialTheme.typography.body1,
                label = firstNameLabel.asString(),
                modifier = Modifier.fillMaxWidth()
            )
            TextFields.OutlinedTextField(
                value = formState.lastName,
                onValueChange = {
                    onFormAction(
                        AuthFormAction.LastNameChanged(it)
                    )
                },
                errorMessage = formState.lastNameError?.asString(),
                textStyle = MaterialTheme.typography.body1,
                label = lastNameLabel.asString(),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        AuthFormActions(
            screenState = screenState,
            onScreenStateToggle = onScreenStateToggle,
            onAuthClick = {
                onFormAction(
                    AuthFormAction.Submit
                )
            }
        )
    }
}

@Composable
private fun AuthFormActions(
    screenState: AuthScreenState,
    onScreenStateToggle: () -> Unit = {},
    onAuthClick: () -> Unit = {}
) {
    Buttons.Button(
        text = (when (screenState) {
            is AuthScreenState.LogIn -> {
                logInText
            }
            is AuthScreenState.Register -> {
                registerText
            }
        }).asString(),
        modifier = Modifier.fillMaxWidth(),
        onClick = onAuthClick
    )
    Buttons.TextButton(
        text = (when (screenState) {
            is AuthScreenState.LogIn -> {
                registerActionText
            }
            is AuthScreenState.Register -> {
                logInActionText
            }
        }).asString(),
        modifier = Modifier.fillMaxWidth(),
        onClick = onScreenStateToggle
    )
}

@Composable
private fun FormResultListener(
    result: Flow<AuthResult>,
    onSuccess: (AuthResult.Success) -> Unit = {},
    onFailed: (AuthResult.Failed) -> Unit = {}
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

@Composable
private fun LoadingPanel(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Card(
            elevation = 8.dp,
            modifier = modifier
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}