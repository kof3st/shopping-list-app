package me.kofesst.android.shoppinglist.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import me.kofesst.android.shoppinglist.presentation.screen.Screen
import me.kofesst.android.shoppinglist.presentation.screen.ScreenConstants
import me.kofesst.android.shoppinglist.presentation.screen.route
import me.kofesst.android.shoppinglist.presentation.screen.withArgs
import me.kofesst.android.shoppinglist.presentation.utils.AppText
import me.kofesst.android.shoppinglist.presentation.utils.activity

val LocalAppState = compositionLocalOf<AppState> {
    error("App state didn't initialize")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp() {
    val appState = LocalAppState.current
    val navController = appState.navController
    val topBarState = remember { appState.topBarState }
    val bottomBarState = remember { appState.bottomBarState }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    DatabaseNotificationsHandle(
        appState = appState
    )
    Scaffold(
        topBar = {
            TopBar(
                state = topBarState,
                navController = navController
            )
        },
        bottomBar = {
            BottomBar(
                state = bottomBarState,
                currentScreenRoute = currentRoute,
                navController = navController
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState
            )
        }
    ) {
        ScreensNavHost(
            navController = navController,
            padding = it
        )
    }
}

@Composable
fun DatabaseNotificationsHandle(
    appState: AppState
) {
    val context = LocalContext.current
    val mainViewModel = hiltViewModel<MainViewModel>(
        viewModelStoreOwner = context.activity!!
    )
    val authState by mainViewModel.authState
    LaunchedEffect(authState) {
        when (authState) {
            AuthState.LoggedOut -> {
                mainViewModel.unsubscribeFromDatabaseChanges()
            }
            AuthState.LoggedIn -> {
                mainViewModel.subscribeToDatabaseChanges { changedListId ->
                    appState.showSnackbar(
                        message = AppText.Toast.listChangedToast(context = context),
                        action = AppText.Action.showChangedListAction(context = context),
                        onActionPerform = {
                            appState.navController.navigate(
                                route = Screen.ListDetails.withArgs(
                                    ScreenConstants.ListDetails.LIST_ID_ARG_NAME to changedListId
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreensNavHost(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.routeName,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Screen.values.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.args
            ) {
                screen.ScreenContent(
                    navController = navController,
                    backStackEntry = it,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    state: TopBarState,
    navController: NavController
) {
    if (state.visible) {
        SmallTopAppBar(
            title = {
                Text(
                    text = state.title(),
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            actions = {
                state.actions.forEach {
                    val action = it.onClick()
                    IconButton(onClick = action) {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.description()
                        )
                    }
                }
            },
            navigationIcon = {
                if (state.hasBackButton) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun BottomBar(
    state: BottomBarState,
    currentScreenRoute: String?,
    navController: NavController
) {
    val bottomBarScreens = Screen.values.filter { screen ->
        screen.bottomBarSettings.visible
    }
    if (state.visible) {
        BottomAppBar(
            tonalElevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            bottomBarScreens.forEach { screen ->
                val isActive = screen.route == currentScreenRoute
                NavigationBarItem(
                    selected = isActive,
                    icon = {
                        Icon(
                            imageVector = screen.bottomBarSettings.icon,
                            contentDescription = screen.bottomBarSettings.title()
                        )
                    },
                    label = {
                        Text(
                            text = screen.bottomBarSettings.title(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        if (!isActive) {
                            navController.navigate(screen.route) {
                                if (currentScreenRoute != null) {
                                    popUpTo(currentScreenRoute) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}