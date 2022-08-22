package me.kofesst.android.shoppinglist.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
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
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.MainViewModel
import me.kofesst.android.shoppinglist.presentation.screen.*
import me.kofesst.android.shoppinglist.presentation.utils.*
import me.kofesst.android.shoppinglist.ui.components.Buttons
import me.kofesst.android.shoppinglist.ui.components.DividerWithText
import me.kofesst.android.shoppinglist.ui.components.TextFields

@Suppress("OPT_IN_IS_NOT_ENABLED")
class HomeScreen(
    routeName: String
) : Screen<HomeViewModel>(
    routeName = routeName,
    topBarSettings = TopBarSettings(
        visible = true,
        title = homeScreenTitle,
        actions = listOf(
            TopBarSettings.Action(
                icon = Icons.Outlined.ExitToApp,
                description = clearSessionText,
                onClick = {
                    val viewModel = hiltViewModel<HomeViewModel>()
                    val context = LocalContext.current
                    val mainViewModel = hiltViewModel<MainViewModel>(
                        viewModelStoreOwner = context.activity!!
                    )
                    val appState = LocalAppState.current
                    return@Action {
                        viewModel.clearSession {
                            mainViewModel.onSignOut()
                            appState.navController.navigate(
                                route = Auth.routeName
                            ) {
                                popUpTo(routeName) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            )
        )
    ),
    bottomBarSettings = BottomBarSettings(
        visible = true,
        icon = Icons.Outlined.Home,
        title = homeScreenTitle
    )
) {
    override val viewModelProducer:
            @Composable (NavHostController, NavBackStackEntry) -> HomeViewModel
        get() = { _, _ -> hiltViewModel() }

    override val content:
            @Composable BoxScope.(NavBackStackEntry, HomeViewModel, Modifier) -> Unit
        get() = { _, _, modifier ->
            HomeContent(
                modifier = modifier
                    .padding(20.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )
        }

    @Composable
    private fun HomeContent(
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current
        val appState = LocalAppState.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = modifier
        ) {
            SearchField(
                onSearch = { query ->
                    if (query.isBlank()) {
                        appState.showSnackbar(
                            message = queryListIdIsEmptyMessage.asString(
                                context = context
                            )
                        )
                    } else {
                        appState.navController.navigate(
                            route = ListDetails.withArgs(
                                ScreenConstants.ListDetails.LIST_ID_ARG_NAME to query
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            DividerWithText(
                text = otherActionText.asString()
            )
            CreateNewListButton(
                onClick = {
                    appState.navController.navigate(
                        route = NewList.routeName
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun SearchField(
        onSearch: (String) -> Unit,
        modifier: Modifier = Modifier,
        contentSpacing: Dp = 14.dp
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        var query by remember {
            mutableStateOf("")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(contentSpacing),
            modifier = modifier
        ) {
            TextFields.OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = shoppingListIdLabel.asString(),
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Buttons.Button(
                text = searchShoppingListText.asString(),
                onClick = {
                    onSearch(query)
                    keyboardController?.hide()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    private fun CreateNewListButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Buttons.Button(
            text = createNewListText.asString(),
            onClick = onClick,
            modifier = modifier
        )
    }
}