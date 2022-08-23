package me.kofesst.android.shoppinglist.presentation.list.create.result

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.screen.Screen
import me.kofesst.android.shoppinglist.presentation.screen.ScreenConstants
import me.kofesst.android.shoppinglist.presentation.utils.AppText
import me.kofesst.android.shoppinglist.ui.components.Buttons

class NewListResultScreen(
    routeName: String
) : Screen<NewListResultViewModel>(
    routeName = routeName,
    args = listOf(
        navArgument(
            name = ScreenConstants.NewListResult.LIST_ID_ARG_NAME
        ) {
            type = NavType.StringType
            defaultValue = "Null list id"
        }
    )
) {
    override val viewModelProducer:
            @Composable (NavHostController, NavBackStackEntry) -> NewListResultViewModel
        get() = { _, _ -> hiltViewModel() }

    override val content:
            @Composable BoxScope.(NavBackStackEntry, NewListResultViewModel, Modifier) -> Unit
        get() = { backStack, _, modifier ->
            val context = LocalContext.current
            val appState = LocalAppState.current
            val listId = this@NewListResultScreen.getStringArg(
                name = ScreenConstants.NewListResult.LIST_ID_ARG_NAME,
                backStackEntry = backStack
            )
            NewListResultContent(
                onShareListIdClick = {
                    val intent = getShareIntent(
                        title = AppText.Title.shareListTitle(context = context),
                        sharing = listId
                    )
                    context.startActivity(intent)
                },
                onGoHomeClick = {
                    appState.navController.navigate(
                        route = ScreenConstants.Home.ROUTE_NAME
                    ) {
                        popUpTo(routeName) {
                            inclusive = true
                        }
                    }
                },
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp)
            )
        }

    @Composable
    fun NewListResultContent(
        onShareListIdClick: () -> Unit,
        onGoHomeClick: () -> Unit,
        modifier: Modifier = Modifier,
        contentSpacing: Dp = 10.dp,
        iconSize: Dp = 72.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = contentSpacing,
                alignment = Alignment.CenterVertically
            ),
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Outlined.Done,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = AppText.listCreatedText(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            NewListResultActions(
                onShareListIdClick = onShareListIdClick,
                onGoHomeClick = onGoHomeClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun NewListResultActions(
        onShareListIdClick: () -> Unit,
        onGoHomeClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Buttons.TextButton(
                text = AppText.Action.copyListIdAction(),
                onClick = onShareListIdClick,
                modifier = Modifier.fillMaxWidth()
            )
            Buttons.TextButton(
                text = AppText.Action.goHomeAction(),
                onClick = onGoHomeClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}