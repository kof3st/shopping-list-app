package me.kofesst.android.shoppinglist.presentation

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.kofesst.android.shoppinglist.presentation.screen.BottomBarSettings
import me.kofesst.android.shoppinglist.presentation.screen.TopBarSettings
import me.kofesst.android.shoppinglist.presentation.utils.UiText

@Stable
class AppState(
    val coroutineState: CoroutineScope,
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val topBarState: TopBarState,
    val bottomBarState: BottomBarState
) {
    fun showSnackbar(
        message: String,
        action: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onDismiss: () -> Unit = {},
        onActionPerform: () -> Unit = {}
    ) {
        coroutineState.launch {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = action,
                duration = duration
            )

            when (result) {
                SnackbarResult.Dismissed -> onDismiss()
                SnackbarResult.ActionPerformed -> onActionPerform()
            }
        }
    }
}

@Composable
fun rememberAppState(
    coroutineState: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    topBarState: TopBarState = TopBarState(),
    bottomBarState: BottomBarState = BottomBarState()
) = AppState(
    coroutineState = coroutineState,
    scaffoldState = scaffoldState,
    navController = navController,
    topBarState = topBarState,
    bottomBarState = bottomBarState
)

class TopBarState(
    visible: Boolean = false,
    hasBackButton: Boolean = false,
    title: UiText = UiText.Static(""),
    actions: List<TopBarSettings.Action> = emptyList()
) {
    var visible by mutableStateOf(visible)
    var hasBackButton by mutableStateOf(hasBackButton)
    var title by mutableStateOf(title)
    var actions by mutableStateOf(actions)

    fun applySettings(settings: TopBarSettings) {
        visible = settings.visible
        hasBackButton = settings.hasBackButton
        title = settings.title
        actions = settings.actions
    }
}

class BottomBarState(
    visible: Boolean = false
) {
    var visible by mutableStateOf(visible)

    fun applySettings(settings: BottomBarSettings) {
        visible = settings.visible
    }
}