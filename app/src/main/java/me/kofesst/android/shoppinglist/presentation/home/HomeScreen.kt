package me.kofesst.android.shoppinglist.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.presentation.*
import me.kofesst.android.shoppinglist.presentation.utils.*
import me.kofesst.android.shoppinglist.ui.components.Buttons
import me.kofesst.android.shoppinglist.ui.components.TextFields

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    CompositionLocalProvider(LocalAppState provides rememberAppState()) {
        HomeScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val appState = LocalAppState.current
    HomeScreenSettings(appState)

    val navController = appState.navController

    Box(modifier = modifier.padding(20.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            SearchListBlock(
                modifier = Modifier.fillMaxWidth()
            ) { listId ->
                if (listId.isNotBlank()) {
                    navController.navigate(
                        Screen.LIST_DETAILS.withArgs(
                            Screen.Constants.ListDetails.LIST_ID_ARG to listId
                        )
                    )
                }
            }
            OtherActionDivider(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )
            CreateNewListButton(
                modifier = Modifier.fillMaxWidth()
            ) {
                navController.navigate(
                    Screen.NEW_LIST.withArgs()
                )
            }
        }
    }
}

@Composable
private fun HomeScreenSettings(appState: AppState) {
    appState.topBarState.title = homeScreenTitle
    appState.topBarState.visible = true
    appState.topBarState.hasBackButton = false
    appState.topBarState.actions = listOf()
}

@Composable
private fun OtherActionDivider(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Divider(
            modifier = Modifier.weight(1.0f)
        )
        Text(
            text = otherActionText.asString().uppercase(),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Light
        )
        Divider(
            modifier = Modifier.weight(1.0f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateNewListButtonPreview() {
    CreateNewListButton(
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CreateNewListButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Buttons.Button(
        text = createNewListText.asString(),
        onClick = onClick,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchListBlockPreview() {
    SearchListBlock(
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun SearchListBlock(
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit = {}
) {
    var listId by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier
    ) {
        TextFields.OutlinedTextField(
            value = listId,
            onValueChange = { listId = it },
            label = shoppingListIdLabel.asString(),
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Buttons.Button(
            text = searchShoppingListText.asString(),
            onClick = { onSearchClick(listId) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}