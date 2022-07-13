package me.kofesst.android.shoppinglist.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.Screen
import me.kofesst.android.shoppinglist.presentation.withArgs

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val appState = LocalAppState.current
    val navController = appState.navController

    var listId by remember {
        mutableStateOf("")
    }

    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            OutlinedTextField(
                value = listId,
                onValueChange = { listId = it },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (listId.isNotBlank()) {
                        navController.navigate(
                            Screen.LIST_DETAILS.withArgs("listId" to listId)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Открыть список покупок")
            }
            Divider()
            Button(
                onClick = {
                    navController.navigate(
                        Screen.NEW_LIST.withArgs()
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Создать новый список покупок")
            }
        }
    }
}