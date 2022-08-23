package me.kofesst.android.shoppinglist.presentation.list.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import me.kofesst.android.shoppinglist.domain.models.ShoppingItem
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.screen.Screen
import me.kofesst.android.shoppinglist.presentation.screen.ScreenConstants
import me.kofesst.android.shoppinglist.presentation.screen.TopBarSettings
import me.kofesst.android.shoppinglist.presentation.screen.withArgs
import me.kofesst.android.shoppinglist.presentation.utils.AppText
import me.kofesst.android.shoppinglist.ui.components.LoadingHandler
import me.kofesst.android.shoppinglist.ui.components.ShoppingListColumn

class NewListScreen(
    routeName: String
) : Screen<NewListViewModel>(
    routeName = routeName,
    topBarSettings = TopBarSettings(
        visible = true,
        hasBackButton = true,
        title = AppText.Title.newListScreenTitle
    )
) {
    override val viewModelProducer:
            @Composable (NavHostController, NavBackStackEntry) -> NewListViewModel
        get() = { _, _ -> hiltViewModel() }

    override val content:
            @Composable BoxScope.(NavBackStackEntry, NewListViewModel, Modifier) -> Unit
        get() = { _, viewModel, modifier ->
            val clipboardManager = LocalClipboardManager.current
            val context = LocalContext.current
            val appState = LocalAppState.current
            val items = viewModel.items
            NewListContent(
                items = items,
                onItemClick = { itemIndex ->
                    appState.navController.navigate(
                        route = NewListItem.withArgs(
                            ScreenConstants.NewListItem.ITEM_INDEX_ARG_NAME to itemIndex
                        )
                    )
                },
                modifier = modifier.fillMaxSize()
            )
            ScreenActionButtons(
                onAddItemClick = {
                    appState.navController.navigate(
                        route = NewListItem.withArgs()
                    )
                },
                onSubmitListClick = {
                    if (items.isEmpty()) {
                        appState.showSnackbar(
                            message = AppText.Toast.cannotSaveEmptyListToast(context = context)
                        )
                    } else {
                        viewModel.saveList { listId ->
                            clipboardManager.setText(
                                annotatedString = AnnotatedString(listId)
                            )
                            appState.showSnackbar(
                                message = AppText.Toast.listIdCopiedToast(context = context)
                            )
                            appState.navController.navigate(
                                route = NewListResult.withArgs(
                                    ScreenConstants.NewListResult.LIST_ID_ARG_NAME to listId
                                )
                            ) {
                                popUpTo(routeName) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
            )
            LoadingHandler(
                viewModel = viewModel
            )
        }

    @Composable
    fun NewListContent(
        items: List<ShoppingItem>,
        onItemClick: (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        if (items.isEmpty()) {
            EmptyListMessagePanel(
                modifier = modifier
            )
        } else {
            ShoppingListColumn(
                items = items,
                onItemClick = onItemClick,
                modifier = modifier
            )
        }
    }

    @Composable
    private fun EmptyListMessagePanel(
        modifier: Modifier = Modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Outlined.Folder,
                contentDescription = null,
                modifier = Modifier.size(72.dp)
            )
            Text(
                text = AppText.emptyListText(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun ScreenActionButtons(
        onAddItemClick: () -> Unit,
        onSubmitListClick: () -> Unit,
        modifier: Modifier = Modifier,
        spaceBetween: Dp = 10.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spaceBetween),
            modifier = modifier
        ) {
            FloatingActionButton(
                onClick = onAddItemClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = AppText.Action.addNewListItemAction()
                )
            }
            FloatingActionButton(
                onClick = onSubmitListClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = AppText.Action.submitListAction()
                )
            }
        }
    }
}