package me.kofesst.android.shoppinglist.presentation.list_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
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
import me.kofesst.android.shoppinglist.domain.models.done.DoneShoppingList
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.screen.Screen
import me.kofesst.android.shoppinglist.presentation.screen.ScreenConstants
import me.kofesst.android.shoppinglist.presentation.screen.TopBarSettings
import me.kofesst.android.shoppinglist.presentation.utils.*
import me.kofesst.android.shoppinglist.ui.components.Buttons
import me.kofesst.android.shoppinglist.ui.components.LoadingStateHandler
import me.kofesst.android.shoppinglist.ui.components.ShoppingListColumn

class ListDetailsScreen(
    routeName: String
) : Screen<ListDetailsViewModel>(
    routeName = routeName,
    topBarSettings = TopBarSettings(
        visible = true,
        hasBackButton = true,
        title = listDetailsScreenTitle
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
                        details = details,
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
        details: DoneShoppingList,
        onCompleteClick: () -> Unit,
        modifier: Modifier = Modifier,
        fabModifier: Modifier = Modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
        ) {
            ListAuthorPanel(
                author = details.author.fullName,
                modifier = Modifier.fillMaxWidth()
            )
            ShoppingListColumn(
                items = details.items,
                modifier = modifier
            )
        }
        CompleteListButton(
            onClick = onCompleteClick,
            modifier = fabModifier
        )
    }

    @Composable
    fun ListAuthorPanel(
        author: String,
        modifier: Modifier = Modifier
    ) {
        Card(
            elevation = 8.dp,
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = listSentFromText.asString(),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = author,
                    style = MaterialTheme.typography.body1,
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
        is NullPointerException -> nullListMessage
        else -> UiText.Static(exception.toString())
    }

    @Composable
    fun ListCompleteConfirmDialog(
        state: Boolean,
        onDismiss: () -> Unit,
        onAccept: () -> Unit
    ) {
        if (state) {
            Surface(elevation = 7.dp) {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    title = {
                        Text(
                            text = confirmDialogTitle.asString(),
                            style = MaterialTheme.typography.h6
                        )
                    },
                    text = {
                        Text(
                            text = confirmDialogMessage.asString(),
                            style = MaterialTheme.typography.body1
                        )
                    },
                    buttons = {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Buttons.TextButton(
                                text = confirmDialogDismissText.asString(),
                                modifier = Modifier.weight(1.0f),
                                onClick = onDismiss
                            )
                            Buttons.TextButton(
                                text = confirmDialogAcceptText.asString(),
                                modifier = Modifier.weight(1.0f),
                                onClick = onAccept
                            )
                        }
                    }
                )
            }
        }
    }
}