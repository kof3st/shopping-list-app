package me.kofesst.android.shoppinglist.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

val LocalAppState = compositionLocalOf<AppState> {
    error("App state didn't initialize")
}

@Composable
fun ShoppingListApp() {
    val appState = LocalAppState.current
    val topBarState = remember { appState.topBarState }
    val bottomBarState = remember { appState.bottomBarState }

    Scaffold(
        scaffoldState = appState.scaffoldState,
        topBar = { TopBar(topBarState) },
        bottomBar = { BottomBar(bottomBarState) }
    ) {
        ScreensNavHost(
            navController = appState.navController,
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
        startDestination = Screen.HOME.route,
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
private fun TopBar(state: TopBarState) {
    AnimatedVisibility(
        visible = state.visible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = state.title.asString(),
                    style = MaterialTheme.typography.h5
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