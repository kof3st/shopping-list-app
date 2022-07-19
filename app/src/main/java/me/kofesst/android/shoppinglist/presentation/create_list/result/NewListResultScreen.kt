package me.kofesst.android.shoppinglist.presentation.create_list.result

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.presentation.AppState
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.utils.copyListIdActionText
import me.kofesst.android.shoppinglist.presentation.utils.goHomeActionText
import me.kofesst.android.shoppinglist.presentation.utils.listCreatedText
import me.kofesst.android.shoppinglist.presentation.utils.shareListTitle
import me.kofesst.android.shoppinglist.ui.components.Buttons

@Composable
fun NewListResultScreen(
    listId: String,
    modifier: Modifier = Modifier
) {
    val appState = LocalAppState.current
    NewListResultScreenSettings(appState)

    Box(modifier = modifier.padding(20.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Outlined.Done,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(72.dp)
            )
            Text(
                text = listCreatedText.asString(),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                val context = LocalContext.current
                Buttons.TextButton(
                    text = copyListIdActionText.asString(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val intent = getShareIntent(
                        title = shareListTitle.asString(context = context),
                        sharing = listId
                    )
                    context.startActivity(intent)
                }

                val navController = appState.navController
                Buttons.TextButton(
                    text = goHomeActionText.asString(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    navController.navigateUp()
                }
            }
        }
    }
}

@Composable
private fun NewListResultScreenSettings(appState: AppState) {
    appState.topBarState.visible = false
}