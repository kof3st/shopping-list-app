package me.kofesst.android.shoppinglist.presentation

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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
    title: UiText = UiText.Static(""),
    hasBackButton: Boolean = false,
    actions: List<Action> = listOf()
) {
    var visible by mutableStateOf(visible)
    var title by mutableStateOf(title)
    var hasBackButton by mutableStateOf(hasBackButton)
    var actions by mutableStateOf(actions)

    class Action(
        val imageVector: ImageVector,
        val contentDescription: UiText,
        val onClick: () -> Unit = {}
    )
}

class BottomBarState(
    visible: Boolean = false
) {
    var visible by mutableStateOf(visible)
}