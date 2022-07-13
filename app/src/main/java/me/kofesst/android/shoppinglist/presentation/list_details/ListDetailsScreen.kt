package me.kofesst.android.shoppinglist.presentation.list_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.utils.LoadingState
import me.kofesst.android.shoppinglist.ui.components.ShoppingListColumn

@Composable
fun ListDetailsScreen(
    listId: String,
    viewModel: ListDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val appState = LocalAppState.current
    val navController = appState.navController

    LaunchedEffect(Unit) {
        viewModel.loadDetails(listId)
    }

    val detailsState by viewModel.detailsState
    var confirmDialogState by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        when (detailsState) {
            is LoadingState.Failed -> {
                ErrorPanel((detailsState as LoadingState.Failed<ShoppingList>).exception)
            }
            is LoadingState.Idle -> {
                LoadingPanel(modifier = Modifier.align(Alignment.Center))
            }
            is LoadingState.Loading -> {
                LoadingPanel(modifier = Modifier.align(Alignment.Center))
            }
            is LoadingState.Loaded -> {
                val details = detailsState as LoadingState.Loaded
                val list = details.value
                if (list == null) {
                    ErrorPanel(Exception("Null list"))
                } else {
                    ShoppingListColumn(
                        list = details.value.items,
                        modifier = modifier
                    )
                    FloatingActionButton(
                        onClick = { confirmDialogState = true },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }

    if (confirmDialogState) {
        CompleteConfirmDialog(
            onDismiss = {
                confirmDialogState = false
            },
            onAccept = {
                confirmDialogState = false
                viewModel.deleteList {
                    navController.navigateUp()
                }
            }
        )
    }
}

@Composable
private fun CompleteConfirmDialog(
    onDismiss: () -> Unit = {},
    onAccept: () -> Unit = {}
) {
    Surface(elevation = 7.dp) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Подтверждение действия",
                    style = MaterialTheme.typography.subtitle1
                )
            },
            text = {
                Text(
                    text = "После выполнения списка покупок он больше не будет доступен. Продолжить?",
                    style = MaterialTheme.typography.body2
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = onDismiss
                    ) {
                        Text("Отмена")
                    }
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = onAccept
                    ) {
                        Text("Да")
                    }
                }
            }
        )
    }
}

@Composable
private fun ErrorPanel(exception: Exception) {
    Text(text = exception.toString())
}

@Composable
private fun LoadingPanel(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
    )
}