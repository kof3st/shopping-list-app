package me.kofesst.android.shoppinglist.presentation.create_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.Screen
import me.kofesst.android.shoppinglist.presentation.withArgs
import me.kofesst.android.shoppinglist.ui.components.ShoppingListColumn

@Composable
fun NewListScreen(
    viewModel: NewListViewModel,
    modifier: Modifier = Modifier
) {
    val appState = LocalAppState.current
    val navController = appState.navController

    val items = viewModel.items
    val clipboardManager = LocalClipboardManager.current
    Box(modifier = modifier) {
        ShoppingListColumn(
            list = items,
            onItemClick = {
                navController.navigate(
                    Screen.CREATE_EDIT_ITEM.withArgs(
                        "itemIndex" to it
                    )
                )
            },
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.CREATE_EDIT_ITEM.withArgs())
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Добавить продукты"
                )
            }
            FloatingActionButton(
                onClick = {
                    viewModel.saveList { listId ->
                        clipboardManager.setText(
                            AnnotatedString(text = listId)
                        )
                        appState.showSnackbar(
                            message = "Номер созданного списка покупок скопирован!",
                            duration = SnackbarDuration.Long
                        )
                        navController.navigateUp()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = "Сохранить"
                )
            }
        }
    }
}