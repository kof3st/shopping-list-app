package me.kofesst.android.shoppinglist.presentation.list_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.models.UserProfile
import me.kofesst.android.shoppinglist.presentation.AppState
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.utils.*
import me.kofesst.android.shoppinglist.ui.components.Buttons
import me.kofesst.android.shoppinglist.ui.components.ShoppingListColumn

@Composable
fun ListDetailsScreen(
    listId: String,
    viewModel: ListDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val appState = LocalAppState.current
    ListDetailsScreenSettings(appState)

    val navController = appState.navController

    LaunchedEffect(Unit) {
        viewModel.loadDetails(listId)
    }

    val detailsState by viewModel.detailsState
    var confirmDialogState by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        ListAuthorDetailsPanel(
            detailsState = detailsState,
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            ListDetails(detailsState = detailsState) {
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
private fun ListDetailsScreenSettings(appState: AppState) {
    appState.topBarState.title = listDetailsScreenTitle
    appState.topBarState.visible = true
    appState.topBarState.hasBackButton = true
    appState.topBarState.actions = listOf()
}

@Composable
private fun ListDetails(
    detailsState: LoadingState<ShoppingList>,
    checkButton: @Composable () -> Unit
) {
    when (detailsState) {
        is LoadingState.Failed -> {
            ErrorPanel()
        }
        is LoadingState.Idle -> {
            LoadingPanel()
        }
        is LoadingState.Loading -> {
            LoadingPanel()
        }
        is LoadingState.Loaded -> {
            val list = detailsState.value
            if (list == null) {
                ErrorPanel()
            } else {
                ShoppingListColumn(
                    list = list.items,
                    modifier = Modifier.fillMaxSize()
                )
                checkButton()
            }
        }
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

@Composable
private fun ErrorPanel() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.SearchOff,
            contentDescription = null,
            modifier = Modifier.size(72.dp)
        )
        Text(
            text = nullListMessage.asString(),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoadingPanel() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ListAuthorDetailsPanel(
    detailsState: LoadingState<ShoppingList>,
    modifier: Modifier = Modifier
) {
    val isLoaded = detailsState is LoadingState.Loaded
    AnimatedVisibility(
        visible = isLoaded,
        enter = slideInVertically(),
        exit = slideOutVertically(),
        modifier = modifier
    ) {
        if (detailsState is LoadingState.Loaded) {
            val author = detailsState.value?.author
            if (author != null) {
                ListAuthorDetails(
                    author = author,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}

@Composable
fun ListAuthorDetails(
    author: UserProfile,
    modifier: Modifier = Modifier,
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
                text = "Отправлено от:",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.ExtraLight,
                textAlign = TextAlign.Center
            )
            Text(
                text = author.fullName,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}