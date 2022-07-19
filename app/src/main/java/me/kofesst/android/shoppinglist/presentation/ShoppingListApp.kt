package me.kofesst.android.shoppinglist.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

val LocalAppState = compositionLocalOf<AppState> {
    error("App state didn't initialize")
}

@Composable
fun ShoppingListApp() {
    val appState = LocalAppState.current
    val navController = appState.navController
    val topBarState = remember { appState.topBarState }
    val bottomBarState = remember { appState.bottomBarState }

    Scaffold(
        scaffoldState = appState.scaffoldState,
        topBar = {
            TopBar(
                state = topBarState,
                navController = navController
            )
        },
        bottomBar = { BottomBar(bottomBarState) }
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
        startDestination = Screen.AUTH.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Screen.values().forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.args
            ) {
                val viewModel = screen.viewModelProducer?.invoke(navController, it)
                screen.content(viewModel, it, Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun TopBar(
    state: TopBarState,
    navController: NavController
) {
    AnimatedVisibility(
        visible = state.visible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = state.title.asString(),
                    style = MaterialTheme.typography.h6
                )
            },
            actions = {
                state.actions.forEach {
                    IconButton(onClick = it.onClick) {
                        Icon(
                            imageVector = it.imageVector,
                            contentDescription = it.contentDescription.asString()
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
private fun BottomBar(state: BottomBarState) {
    AnimatedVisibility(
        visible = state.visible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        BottomAppBar(
            elevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Screen.values()
        }
    }
}