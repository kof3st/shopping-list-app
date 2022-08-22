package me.kofesst.android.shoppinglist.presentation.list.complete

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.screen.Screen
import me.kofesst.android.shoppinglist.presentation.screen.ScreenConstants
import me.kofesst.android.shoppinglist.presentation.screen.TopBarSettings
import me.kofesst.android.shoppinglist.presentation.utils.AppText
import me.kofesst.android.shoppinglist.presentation.utils.UiText
import me.kofesst.android.shoppinglist.ui.components.Buttons
import me.kofesst.android.shoppinglist.ui.components.EditingShoppingListColumn
import me.kofesst.android.shoppinglist.ui.components.LoadingStateHandler

class ListDetailsScreen(
    routeName: String
) : Screen<ListDetailsViewModel>(
    routeName = routeName,
    topBarSettings = TopBarSettings(
        visible = true,
        hasBackButton = true,
        title = AppText.Title.listDetailsScreenTitle
    ),
    args = listOf(
        navArgument(
            name = ScreenConstants.ListDetails.LIST_ID_ARG_NAME
        ) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
) {
    override val viewModelProducer:
            @Composable (NavHostController, NavBackStackEntry) -> ListDetailsViewModel
        get() = { _, _ -> hiltViewModel() }

    override val content:
            @Composable BoxScope.(NavBackStackEntry, ListDetailsViewModel, Modifier) -> Unit
        get() = { backStack, viewModel, modifier ->
            val listId = getStringArg(
                name = ScreenConstants.ListDetails.LIST_ID_ARG_NAME,
                backStackEntry = backStack
            )
            LaunchedEffect(Unit) {
                viewModel.loadDetails(listId)
            }
            var confirmDialogState by remember {
                mutableStateOf(false)
            }
            val detailsState by viewModel.detailsState
            LoadingStateHandler(
                state = detailsState,
                errorMessageProducer = { getDetailsStateErrorMessage(it) },
                content = { details ->
                    ListDetailsContent(
                        list = details,
                        onCompleteClick = {
                            confirmDialogState = true
                        },
                        modifier = Modifier.fillMaxSize(),
                        fabModifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(20.dp)
                    )
                },
                modifier = modifier.fillMaxSize()
            )
            val appState = LocalAppState.current
            ListCompleteConfirmDialog(
                state = confirmDialogState,
                onDismiss = {
                    confirmDialogState = false
                },
                onAccept = {
                    confirmDialogState = false
                    viewModel.completeList {
                        appState.navController.navigateUp()
                    }
                }
            )
        }

    @Composable
    private fun ListDetailsContent(
        list: ShoppingList,
        onCompleteClick: () -> Unit,
        modifier: Modifier = Modifier,
        fabModifier: Modifier = Modifier
    ) {
        val isShowOnly = list.done
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
        ) {
            ListAuthorPanel(
                author = list.author.fullName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            EditingShoppingListColumn(
                items = list.items,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (!isShowOnly) {
            CompleteListButton(
                onClick = onCompleteClick,
                modifier = fabModifier
            )
        }
    }

    @Composable
    fun ListAuthorPanel(
        author: String,
        modifier: Modifier = Modifier
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = AppText.listSentFromText(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = author,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun CompleteListButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null
            )
        }
    }

    private fun getDetailsStateErrorMessage(exception: Exception) = when (exception) {
        is NullPointerException -> AppText.nullListText
        else -> UiText.Static(exception.toString())
    }

    @Composable
    fun ListCompleteConfirmDialog(
        state: Boolean,
        onDismiss: () -> Unit,
        onAccept: () -> Unit
    ) {
        if (state) {
            Surface(tonalElevation = 7.dp) {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    title = {
                        Text(
                            text = AppText.Title.confirmDialogTitle(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    text = {
                        Text(
                            text = AppText.confirmDialogMessageText(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    confirmButton = {
                        Buttons.TextButton(
                            text = AppText.Action.confirmDialogDismissAction(),
                            onClick = onDismiss
                        )
                    },
                    dismissButton = {
                        Buttons.TextButton(
                            text = AppText.Action.confirmDialogAcceptAction(),
                            onClick = onAccept
                        )
                    }
                )
            }
        }
    }
}