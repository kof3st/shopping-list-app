package me.kofesst.android.shoppinglist.presentation.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import me.kofesst.android.shoppinglist.R
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.screen.*
import me.kofesst.android.shoppinglist.presentation.utils.AppText
import me.kofesst.android.shoppinglist.ui.components.DividerWithText
import me.kofesst.android.shoppinglist.ui.components.LoadingStateHandler
import me.kofesst.android.shoppinglist.ui.components.LottieMessage
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
                    if (userLists.isEmpty()) {
                        EmptySectionsPanel()
                    }
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
            if (activeLists.isNotEmpty()) {
                stickyHeader {
                    SectionHeader(
                        text = AppText.activeListsSectionText()
                    )
                }
                items(activeLists) { activeList ->
                    ShoppingListItem(
                        list = activeList,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onItemClick(activeList) }
                    )
                }
            }
            if (doneLists.isNotEmpty()) {
                stickyHeader {
                    SectionHeader(
                        text = AppText.doneListsSectionText()
                    )
                }
                items(doneLists) { doneList ->
                    ShoppingListItem(
                        list = doneList,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onItemClick(doneList) }
                    )
                }
            }
        }
    }

    @Composable
    private fun SectionHeader(
        text: String,
        textPadding: Dp = 10.dp
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
        ) {
            DividerWithText(
                text = text,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = textPadding)
            )
        }
    }

    @Composable
    private fun EmptySectionsPanel() {
        LottieMessage(
            lottieRes = R.raw.empty_cart_lottie,
            message = AppText.emptyListsSectionText()
        )
    }
}