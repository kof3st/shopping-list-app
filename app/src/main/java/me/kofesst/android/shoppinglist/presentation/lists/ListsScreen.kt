package me.kofesst.android.shoppinglist.presentation.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.screen.*
import me.kofesst.android.shoppinglist.presentation.utils.AppText
import me.kofesst.android.shoppinglist.ui.components.DividerWithText
import me.kofesst.android.shoppinglist.ui.components.LoadingStateHandler
import me.kofesst.android.shoppinglist.ui.components.ShoppingListItem

@Suppress("OPT_IN_IS_NOT_ENABLED")
class ListsScreen(
    routeName: String
) : Screen<ListsViewModel>(
    routeName = routeName,
    topBarSettings = TopBarSettings(
        visible = true,
        title = AppText.Title.listsScreenTitle
    ),
    bottomBarSettings = BottomBarSettings(
        visible = true,
        icon = Icons.Outlined.Checklist,
        title = AppText.Title.listsScreenTitle
    )
) {
    override val viewModelProducer:
            @Composable (NavHostController, NavBackStackEntry) -> ListsViewModel
        get() = { _, _ -> hiltViewModel() }

    override val content:
            @Composable BoxScope.(NavBackStackEntry, ListsViewModel, Modifier) -> Unit
        get() = { _, viewModel, modifier ->
            LaunchedEffect(Unit) {
                viewModel.loadLists()
            }

            val appState = LocalAppState.current
            val lists by viewModel.lists
            LoadingStateHandler(
                state = lists,
                content = { userLists ->
                    ListsContent(
                        lists = userLists,
                        modifier = Modifier.fillMaxWidth(),
                        onItemClick = { list ->
                            appState.navController.navigate(
                                route = ListDetails.withArgs(
                                    ScreenConstants.ListDetails.LIST_ID_ARG_NAME to list.id
                                )
                            )
                        }
                    )
                },
                modifier = modifier.fillMaxWidth()
            )
        }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ListsContent(
        lists: List<ShoppingList>,
        onItemClick: (ShoppingList) -> Unit,
        modifier: Modifier = Modifier,
        contentPadding: Dp = 20.dp,
        contentSpacing: Dp = 16.dp
    ) {
        val activeLists = lists.filter { !it.done }
        val doneLists = lists.filter { it.done }
        LazyColumn(
            contentPadding = PaddingValues(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(contentSpacing),
            modifier = modifier
        ) {
            stickyHeader {
                DividerWithText(
                    text = AppText.activeListsSectionText()
                )
            }
            if (activeLists.isNotEmpty()) {
                items(activeLists) { activeList ->
                    ShoppingListItem(
                        list = activeList,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onItemClick(activeList) }
                    )
                }
            } else {
                item {
                    EmptySectionPanel()
                }
            }
            stickyHeader {
                DividerWithText(
                    text = AppText.doneListsSectionText()
                )
            }
            if (doneLists.isNotEmpty()) {
                items(doneLists) { doneList ->
                    ShoppingListItem(
                        list = doneList,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onItemClick(doneList) }
                    )
                }
            } else {
                item {
                    EmptySectionPanel()
                }
            }
        }
    }

    @Composable
    private fun EmptySectionPanel() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Outlined.Eco,
                contentDescription = null,
                modifier = Modifier.size(72.dp)
            )
            Text(
                text = AppText.emptyListsSectionText(),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}