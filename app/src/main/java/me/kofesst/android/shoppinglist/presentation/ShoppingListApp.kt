package me.kofesst.android.shoppinglist.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import me.kofesst.android.shoppinglist.presentation.screen.Screen
import me.kofesst.android.shoppinglist.presentation.screen.route

val LocalAppState = compositionLocalOf<AppState> {
    error("App state didn't initialize")
}

@Composable
fun ShoppingListApp() {
    val appState = LocalAppState.current
    val navController = appState.navController
    val topBarState = remember { appState.topBarState }
    val bottomBarState = remember { appState.bottomBarState }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        scaffoldState = appState.scaffoldState,
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
        }
    ) {
        ScreensNavHost(
            navController = navController,
            padding = it
        )
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

@Composable
private fun TopBar(
    state: TopBarState,
    navController: NavController
) {
    if (state.visible) {
        TopAppBar(
            title = {
                Text(
                    text = state.title.asString(),
                    style = MaterialTheme.typography.h6
                )
            },
            actions = {
                state.actions.forEach {
                    val action = it.onClick()
                    IconButton(onClick = action) {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.description.asString()
                        )
                    }
                }
            },
            navigationIcon = if (state.hasBackButton) {
                {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            } else {
                null
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
            elevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            bottomBarScreens.forEach { screen ->
                val isActive = screen.route == currentScreenRoute
                BottomNavigationItem(
                    selected = isActive,
                    icon = {
                        Icon(
                            imageVector = screen.bottomBarSettings.icon,
                            contentDescription = screen.bottomBarSettings.title.asString()
                        )
                    },
                    label = {
                        Text(
                            text = screen.bottomBarSettings.title.asString(),
                            style = MaterialTheme.typography.body2
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