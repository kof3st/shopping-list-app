package me.kofesst.android.shoppinglist.presentation.create_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.presentation.*
import me.kofesst.android.shoppinglist.presentation.utils.*
import me.kofesst.android.shoppinglist.ui.components.ShoppingListColumn

@Composable
fun NewListScreen(
    viewModel: NewListViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val appState = LocalAppState.current
    val navController = appState.navController

    val items = viewModel.items
    NewListScreenSettings(
        appState = appState,
        onSubmitClick = {
            if (items.isEmpty()) {
                appState.showSnackbar(
                    message = cannotSaveEmptyListMessage.asString(context = context),
                    duration = SnackbarDuration.Short
                )
            } else {
                viewModel.saveList { listId ->
                    navController.navigate(
                        Screen.NEW_LIST_RESULT.withArgs(
                            Screen.Constants.NewListResult.LIST_ID_ARG to listId
                        )
                    ) {
                        popUpTo(Screen.NEW_LIST.routeName) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    )
    Box(modifier = modifier) {
        ShoppingListColumn(
            items = items,
            onItemClick = {
                navController.navigate(
                    Screen.CREATE_EDIT_ITEM.withArgs(
                        Screen.Constants.CreateEditItem.ITEM_INDEX_ARG to it
                    )
                )
            },
            modifier = Modifier.fillMaxSize()
        )
        if (items.isEmpty()) {
            EmptyListMessage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp)
            )
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(
                    Screen.CREATE_EDIT_ITEM.withArgs()
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = addItemText.asString()
            )
        }
    }
}

@Composable
private fun NewListScreenSettings(
    appState: AppState,
    onSubmitClick: () -> Unit
) {
    appState.topBarState.visible = true
    appState.topBarState.title = newListScreenTitle
    appState.topBarState.hasBackButton = true
    appState.topBarState.actions = listOf(
        TopBarState.Action(
            imageVector = Icons.Outlined.Save,
            contentDescription = submitListText,
            onClick = onSubmitClick
        )
    )
}

@Composable
private fun EmptyListMessage(
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
            text = emptyListMessage.asString(),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}